package com.ocdsoft.bacta.swg.router;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.assistedinject.Assisted;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.client.SoeUdpClient;
import com.ocdsoft.bacta.swg.network.soe.message.ReliableNetworkMessage;
import com.ocdsoft.bacta.swg.network.soe.message.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.network.swg.util.ClientString;
import com.ocdsoft.bacta.swg.shared.util.SOECRC32;
import com.ocdsoft.network.annotation.ControllerScan;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;
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
import java.util.*;

@ControllerScan(target = "com.ocdsoft.bacta")
public class SwgDevelopMessageRouter<Client extends SoeUdpClient> implements SwgMessageRouter<Client> {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private final TIntObjectMap<SwgMessageController<Client>> controllers = new TIntObjectHashMap<SwgMessageController<Client>>();

    private VelocityEngine ve = null;

    private final Map<String, String> existingControllerMap = new HashMap<String, String>();
    private final List<String> existingControllers = new ArrayList<String>();
    private final List<Class<? extends ReliableNetworkMessage>> existingMessages = new ArrayList<Class<? extends ReliableNetworkMessage>>();

    private final ServerType serverEnv;

    private final Injector injector;

    @Inject
    public SwgDevelopMessageRouter(Injector injector, @Assisted ServerType serverEnv) {
        this.injector = injector;
        this.serverEnv = serverEnv;

        loadControllers();
    }

    @Override
    public void routeMessage(int opcode, Client client, SoeByteBuf message) {

        SwgMessageController<Client> controller = controllers.get(opcode);

        if (controller == null) {

            handleMissingController(opcode, message);

        } else {

            try {
                logger.debug("Routing to " + controller.getClass().getSimpleName());
                controller.handleIncoming(client, message);
            } catch (Exception e) {
                logger.error("SWG Message Handling", e);
            }
        }
    }

    private void handleMissingController(int opcode, ByteBuf message) {

        writeTemplates(opcode, message);

        String propertyName = Integer.toHexString(opcode);

        logger.error("Unhandled SWG Message: '" + ClientString.get(propertyName) + "' 0x" + propertyName);
        logger.error(SoeMessageUtil.bytesToHex(message));
    }

    private void loadControllers() {

        ControllerScan scanAnnotiation = getClass().getAnnotation(ControllerScan.class);

        if (scanAnnotiation == null) {
            logger.error("Missing @ControllerScan annotation, unable to load controllers");
            return;
        }

        Reflections reflections = new Reflections(scanAnnotiation.target());

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

                SwgController controllerAnnotiation = controllerClass.getAnnotation(SwgController.class);

                if (controllerAnnotiation == null) {
                    logger.info("Missing @SwgController annotation, discarding: " + controllerClass.getName());
                    continue;
                }

                Class<?> handledMessageClass = controllerAnnotiation.handles();

                if (handledMessageClass != null) {
                    synchronized (existingControllerMap) {
                        logger.trace("Adding Controller for " + serverEnv + ": " + controllerClass.getName());
                        existingControllerMap.put(handledMessageClass.getSimpleName(), controllerClass.getSimpleName());
                    }
                }

                boolean match = false;

                for (ServerType server : controllerAnnotiation.server()) {
                    if (server == serverEnv) {
                        match = true;
                    }
                }

                if (!match) {
                    continue;
                }

                SwgMessageController<Client> controller = injector.getInstance(controllerClass);

                int hash = SOECRC32.hashCode(handledMessageClass.getSimpleName());

                if (!controllers.containsKey(hash)) {
//					logger.debug("Adding SWG controller for: " + handledMessageClass.getSimpleName());

                    synchronized (controllers) {
                        controllers.put(hash, controller);
                    }
                }
            } catch (Exception e) {
                logger.error("Unable to add controller: " + controllerClass.getSimpleName(), e);
            }
        }
    }

    private void writeTemplates(int opcode, ByteBuf buffer) {

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

    private void writeMessage(String messageName, ByteBuf buffer) throws Exception {

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
}
