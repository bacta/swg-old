package com.ocdsoft.bacta.swg.router;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.engine.network.ControllerScan;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.message.ReliableNetworkMessage;
import com.ocdsoft.bacta.soe.util.ClientString;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.ByteBuffer;

@ControllerScan(target = "com.ocdsoft.bacta.swg.controller")
public final class SwgDevelopMessageRouter<Connection extends SoeUdpConnection> extends SwgProductionMessageRouter<Connection> {
    private static final Logger logger = LoggerFactory.getLogger(SwgDevelopMessageRouter.class);
    private VelocityEngine ve = null;

    @Inject
    public SwgDevelopMessageRouter(Injector injector, GameServerState serverState) {
        super(injector, serverState);
    }

    @Override
    protected void handleMissingController(int opcode, ByteBuffer buffer) {

        writeTemplates(opcode, buffer);

        super.handleMissingController(opcode, buffer);
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
}
