package com.ocdsoft.bacta.swg.router;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.assistedinject.Assisted;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.client.SoeUdpClient;
import com.ocdsoft.bacta.swg.network.soe.message.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.network.swg.util.ClientString;
import com.ocdsoft.bacta.swg.shared.util.SOECRC32;
import com.ocdsoft.network.annotation.ControllerScan;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Set;

@ControllerScan(target = "com.ocdsoft.bacta")
public class SwgProductionMessageRouter<Client extends SoeUdpClient> implements SwgMessageRouter<Client> {

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private final TIntObjectMap<SwgMessageController<Client>> controllers = new TIntObjectHashMap<SwgMessageController<Client>>();

    private Injector injector;

    private ServerType serverEnv;

    @Inject
    public SwgProductionMessageRouter(Injector injector, @Assisted ServerType serverEnv) {
        this.injector = injector;
        this.serverEnv = serverEnv;
        //loadControllers(); //TODO: Fix class with dev fixes
    }

    @Override
    public void routeMessage(int opcode, Client client, SoeByteBuf message) {

        SwgMessageController<Client> controller = controllers.get(opcode);
        if (controller == null) {

            String propertyName = Integer.toHexString(opcode);

            logger.error("Unhandled SWG Message: '" + ClientString.get(propertyName) + "' 0x" + propertyName);
            logger.error(SoeMessageUtil.bytesToHex(message));

        } else {

            try {
                controller.handleIncoming(client, message);
            } catch (Exception e) {
                logger.error("SWG Message Handling", e);
            }
        }
    }

    private void loadControllers() {

        ControllerScan scanAnnotiation = getClass().getAnnotation(ControllerScan.class);

        if (scanAnnotiation == null) {
            logger.error("Missing @ControllerScan annotation, unable to load controllers");
            return;
        }

        Reflections reflections = new Reflections(scanAnnotiation.target());

        Set<Class<? extends SwgMessageController>> subTypes = reflections.getSubTypesOf(SwgMessageController.class);

        Iterator<Class<? extends SwgMessageController>> iter = subTypes.iterator();

        while (iter.hasNext()) {

            try {
                Class<? extends SwgMessageController> controllerClass = iter.next();

                SwgController controllerAnnotiation = controllerClass.getAnnotation(SwgController.class);

                if (controllerAnnotiation == null) {
                    logger.info("Missing @SwgController annotation, discarding: " + controllerClass.getName());
                    continue;
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

                SwgMessageController controller = injector.getInstance(controllerClass);

                Class<?> handledMessageClass = controllerAnnotiation.handles();

                int hash = SOECRC32.hashCode(handledMessageClass.getSimpleName());

                if (!controllers.containsKey(hash)) {
//					logger.info("Adding SWG controller for: " + handledMessageClass.getSimpleName());

                    synchronized (controllers) {
                        controllers.put(hash, controller);
                    }
                }
            } catch (Exception e) {
                logger.error("Unable to add controller", e);
            }
        }
    }

}
