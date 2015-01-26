package com.ocdsoft.bacta.swg.router;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.engine.network.ControllerScan;
import com.ocdsoft.bacta.soe.ServerState;
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
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.*;

@ControllerScan(target = "com.ocdsoft.bacta.swg.cu.controller")
public class SwgProductionMessageRouter<Connection extends SoeUdpConnection> implements SwgMessageRouter<Connection> {
    private static final Logger logger = LoggerFactory.getLogger(SwgProductionMessageRouter.class);

    protected final TIntObjectMap<ControllerData> controllers = new TIntObjectHashMap<>();

    protected final Map<String, String> existingControllerMap = new HashMap<>();
    protected final List<String> existingControllers = new ArrayList<>();
    protected final List<Class<? extends ReliableNetworkMessage>> existingMessages = new ArrayList<>();

    protected final ServerType serverEnv;

    @Inject
    public SwgProductionMessageRouter(Injector injector, ServerState serverState) {
        this.serverEnv = serverState.getServerType();

        loadControllers(injector);
    }

    @Override
    public void routeMessage(byte priority, int opcode, Connection connection, ByteBuffer buffer) {

        ControllerData controllerData = controllers.get(opcode);
        if(controllerData != null) {
            SwgMessageController controller = controllerData.getSwgMessageController();
            Constructor<? extends GameNetworkMessage> constructor = controllerData.getConstructor();
            try {

                GameNetworkMessage message = constructor.newInstance(buffer);


                try {

                    logger.debug("Routing to " + controller.getClass().getSimpleName());

                    controller.handleIncoming(connection, message);

                } catch (Exception e) {
                    logger.error("SWG Message Handling", e);
                }


            } catch (Exception e) {
                logger.error("Unable to create incoming message", e);
            }
        } else {
            handleMissingController(opcode, buffer);
        }
    }

    protected void handleMissingController(int opcode, ByteBuffer buffer) {

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
                Constructor constructor = handledMessageClass.getConstructor(ByteBuffer.class);

                ControllerData newControllerData = new ControllerData(controller, constructor);

                if (!controllers.containsKey(hash)) {
                    String propertyName = Integer.toHexString(hash);
                    logger.debug("Adding Controller for " + serverEnv + ": " + controllerClass.getName() + " " + ClientString.get(propertyName) + "' 0x" + propertyName);

                    synchronized (controllers) {
                        controllers.put(hash, newControllerData);
                    }
                }
            } catch (Exception e) {
                logger.error("Unable to add controller: " + controllerClass.getSimpleName(), e);
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
