package com.ocdsoft.bacta.swg.router;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.assistedinject.Assisted;
import com.ocdsoft.bacta.engine.network.ControllerScan;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.ReliableNetworkMessage;
import com.ocdsoft.bacta.soe.router.SwgMessageRouter;
import com.ocdsoft.bacta.soe.util.ClientString;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.SwgController;
import com.ocdsoft.bacta.swg.controller.SwgMessageController;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.*;

@ControllerScan(target = "com.ocdsoft.bacta.swg.controller")
public final class SwgDevelopMessageRouter<Connection extends SoeUdpConnection> implements SwgMessageRouter<Connection> {
    private static final Logger logger = LoggerFactory.getLogger(SwgDevelopMessageRouter.class);

    private final TIntObjectMap<ControllerData> controllers = new TIntObjectHashMap<>();

    private VelocityEngine ve = null;

    private final Map<String, String> existingControllerMap = new HashMap<>();
    private final List<String> existingControllers = new ArrayList<>();
    private final List<Class<? extends ReliableNetworkMessage>> existingMessages = new ArrayList<>();

    private final ServerType serverEnv;
    private final boolean developmentMode;

    @Inject
    public SwgDevelopMessageRouter(Injector injector, @Assisted ServerType serverEnv, @Assisted boolean developmentMode) {
        this.serverEnv = serverEnv;
        this.developmentMode = developmentMode;

        loadControllers(injector);
    }

    @Override
    public void routeMessage(byte priority, int opcode, Connection connection, ByteBuffer buffer) {

        ControllerData controllerData = controllers.get(opcode);
        SwgMessageController controller = controllerData.getSwgMessageController();
        Constructor<? extends GameNetworkMessage> constructor = controllerData.getConstructor();
        try {

            GameNetworkMessage message = constructor.newInstance();
            message.deserialize(buffer);

            if (controller != null) {

                try {

                    logger.debug("Routing to " + controller.getClass().getSimpleName());

                    controller.handleIncoming(connection, message);

                } catch (Exception e) {
                    logger.error("SWG Message Handling", e);
                }
            } else {
                handleMissingController(opcode, buffer);
            }

        } catch (Exception e) {
            logger.error("Unable to create incoming message", e);
        }
    }

    private void handleMissingController(int opcode, ByteBuffer buffer) {

        if(developmentMode) {
            writeTemplates(opcode, buffer);
        }

        String propertyName = Integer.toHexString(opcode);

        logger.error("Unhandled SWG Message: '" + ClientString.get(propertyName) + "' 0x" + propertyName);
        logger.error(SoeMessageUtil.bytesToHex(buffer));
    }

    private void loadControllers(final Injector injector) {

        ControllerScan scanAnnotation = getClass().getAnnotation(ControllerScan.class);

        if (scanAnnotation == null) {
            logger.error("Missing @ControllerScan annotation, unable to load controllers");
            return;
        }

        Reflections reflections = new Reflections(scanAnnotation.target());

        Set<Class<? extends ReliableNetworkMessage>> swgMessages = reflections.getSubTypesOf(ReliableNetworkMessage.class);

        Iterator<Class<? extends ReliableNetworkMessage>> messageIter = swgMessages.iterator();
        while (messageIter.hasNext()) {

            Class<? extends ReliableNetworkMessage> messageClass = messageIter.next();

            synchronized (existingMessages) {
                existingMessages.add(messageClass);
            }
        }

        Set<Class<? extends SwgMessageController>> subTypes = reflections.getSubTypesOf(SwgMessageController.class);

        Iterator<Class<? extends SwgMessageController>> iter = subTypes.iterator();
        while (iter.hasNext()) {

            Class<? extends SwgMessageController> controllerClass = iter.next();

            try {
                synchronized (existingControllers) {
                    existingControllers.add(controllerClass.getSimpleName());
                }

                SwgController controllerAnnotation = controllerClass.getAnnotation(SwgController.class);

                if (controllerAnnotation == null) {
                    logger.info("Missing @SwgController annotation, discarding: " + controllerClass.getName());
                    continue;
                }

                Class<?> handledMessageClass = controllerAnnotation.handles();

                if (handledMessageClass != null) {
                    synchronized (existingControllerMap) {
                        logger.trace("Adding Controller for " + serverEnv + ": " + controllerClass.getName());
                        existingControllerMap.put(handledMessageClass.getSimpleName(), controllerClass.getSimpleName());
                    }
                }

                boolean match = false;

                for (ServerType server : controllerAnnotation.server()) {
                    if (server == serverEnv) {
                        match = true;
                    }
                }

                if (!match) {
                    continue;
                }


                SwgMessageController controller = injector.getInstance(controllerClass);
                int hash = SOECRC32.hashCode(handledMessageClass.getSimpleName());
                Constructor constructor = handledMessageClass.getConstructor();

                ControllerData newControllerData = new ControllerData(controller, constructor);

                if (!controllers.containsKey(hash)) {
//					logger.debug("Adding SWG controller for: " + handledMessageClass.getSimpleName());

                    synchronized (controllers) {
                        controllers.put(hash, newControllerData);
                    }
                }
            } catch (Exception e) {
                logger.error("Unable to add controller: " + controllerClass.getSimpleName(), e);
            }
        }
    }

    private void writeTemplates(int opcode, ByteBuffer buffer) {

        initializeTemplating();

        String messageName = ClientString.get(opcode);
        String messageClass = "";

        if (messageName.isEmpty() || messageName.equalsIgnoreCase("unknown")) {
            logger.error("Unknown message opcode: 0x" + Integer.toHexString(opcode));
            return;
        }

        boolean controllerExists = existingControllerMap.containsKey(messageName);

        boolean messageExists = false;
        for (Class<? extends ReliableNetworkMessage> existingMessage : existingMessages) {
            if (existingMessage.getSimpleName().equalsIgnoreCase(messageName)) {
                messageExists = true;
                messageClass = existingMessage.getName();
                break;
            }
        }

        if (!messageExists) {

            try {
                writeMessage(messageName, buffer);
                messageClass = "com.ocdsoft.bacta.swg.server." + serverEnv.getGroup() + ".message." + messageName;
            } catch (Exception e) {
                logger.error("Unable to write message", e);
            }
        }

        if (!controllerExists) {
            try {
                writeController(messageName, messageClass);
            } catch (Exception e) {
                logger.error("Unable to write controller", e);
            }
        }
    }

    private void writeController(String messageName, String messageClasspath) throws Exception {

        String className = messageName + "Controller";

        Template t = ve.getTemplate("swg/src/main/resources/templates/swgcontroller.vm");

        VelocityContext context = new VelocityContext();

        context.put("packageName", "com.ocdsoft.bacta.swg.server." + serverEnv.getGroup() + ".controller");
        context.put("messageClasspath", messageClasspath);
        context.put("serverType", "ServerType." + serverEnv);
        context.put("messageName", messageName);
        context.put("messageNameClass", messageName + ".class");
        context.put("className", className);

        /* lets render a template */
        String outFileName = System.getProperty("user.dir") + "/swg/src/main/java/com/ocdsoft/bacta/swg/server/" + serverEnv.getGroup() + "/controller/" + className + ".java";
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFileName)));

        if (!ve.evaluate(context, writer, t.getName(), "")) {
            throw new Exception("Failed to convert the template into class.");
        }

        t.merge(context, writer);

        writer.flush();
        writer.close();
    }

    private void writeMessage(String messageName, ByteBuffer buffer) throws Exception {

        Template t = ve.getTemplate("swg/src/main/resources/templates/swgmessage.vm");

        VelocityContext context = new VelocityContext();

        context.put("packageName", "com.ocdsoft.bacta.swg.server." + serverEnv.getGroup() + ".message");
        context.put("messageName", messageName);

        String messageStruct = SoeMessageUtil.makeMessageStruct(buffer);
        context.put("messageStruct", messageStruct);

        context.put("priority", "0x" + Integer.toHexString(buffer.getShort(6)));
        context.put("opcode", "0x" + Integer.toHexString(buffer.getInt(8)));

        /* lets render a template */

        String outFileName = System.getProperty("user.dir") + "/swg/src/main/java/com/ocdsoft/bacta/swg/server/" + serverEnv.getGroup() + "/message/" + messageName + ".java";
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFileName)));

        if (!ve.evaluate(context, writer, t.getName(), "")) {
            throw new Exception("Failed to convert the template into class.");
        }

        t.merge(context, writer);

        writer.flush();
        writer.close();

    }


    private void initializeTemplating() {
        synchronized (controllers) {
            if (ve == null) {
                ve = new VelocityEngine();
                ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, logger);
                ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");


                ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
                ve.init();
            }
        }
    }

    private class ControllerData {
        @Getter
        final private SwgMessageController swgMessageController;

        @Getter
        final private Constructor constructor;

        public ControllerData(final SwgMessageController swgMessageController,
                          final Constructor constructor) {
            this.swgMessageController = swgMessageController;
            this.constructor = constructor;

        }
    }

}
