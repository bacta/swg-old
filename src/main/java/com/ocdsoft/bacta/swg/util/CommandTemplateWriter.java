package com.ocdsoft.bacta.swg.util;

import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.shared.util.ObjectControllerNames;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.ByteBuffer;

/**
 * Created by kburkhardt on 1/31/15.
 */
public class CommandTemplateWriter {

    private static final Logger logger = LoggerFactory.getLogger(CommandTemplateWriter.class);

    private final VelocityEngine ve;
    private final ServerType serverEnv;

    private final String controllerClassPath;
    private final String controllerFilePath;

    private final String messageFilePath;
    private final String messageClassPath;

    private final String controllerFile;

    public CommandTemplateWriter(final ServerType serverEnv) {

        this.serverEnv = serverEnv;

        ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, logger);
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());


        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
        ve.init();

        controllerClassPath = System.getProperty("base.classpath") + ".controller." + serverEnv.name().toLowerCase() + ".object.command";
        controllerFilePath = System.getProperty("template.filepath") + "/src/main/java/" +
                System.getProperty("base.classpath").replace(".", "/") +
                "/controller/" + serverEnv.name().toLowerCase() + "/object/command/";
        
        messageClassPath = System.getProperty("base.classpath") + ".message." + serverEnv.name().toLowerCase() + ".object.command";
        messageFilePath = System.getProperty("template.filepath") + "/src/main/java/" +
                System.getProperty("base.classpath").replace(".", "/") +
                "/message/" + serverEnv.name().toLowerCase() + "/object/command/";

        controllerFile = System.getProperty("template.filepath") + "/src/main/resources/ObjectControllers.lst";
    }

    public void createFiles(int opcode, ByteBuffer buffer, String tangibleName, String objectControllerName) {

        String messageName = ObjectControllerNames.get(opcode);
        
        if (messageName.isEmpty() || messageName.equalsIgnoreCase("unknown")) {
            logger.error("Unknown message opcode: 0x" + Integer.toHexString(opcode));
            return;
        }

        writeMessage(opcode, messageName, buffer, objectControllerName);
        writeController(opcode, messageName, tangibleName);
    }
    
    private void writeMessage(int opcode, String messageName, ByteBuffer buffer, String objectControllerName)  {

        String outFileName = messageFilePath + messageName + ".java";
        File file = new File(outFileName);
        if(file.exists()) {
            logger.info("'" + messageName + "' already exists");
            return;
        }
        
        Template t = ve.getTemplate("/templates/swgcommandmessage.vm");

        VelocityContext context = new VelocityContext();

        context.put("packageName", messageClassPath);
        context.put("objectControllerName", objectControllerName);
        context.put("messageName", messageName);
        context.put("controllerid", Integer.toHexString(opcode));

        String messageStruct = SoeMessageUtil.makeMessageStruct(buffer);
        context.put("messageStruct", messageStruct);
        /* lets render a template */

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFileName)));

            if (!ve.evaluate(context, writer, t.getName(), "")) {
                throw new Exception("Failed to convert the template into class.");
            }

            t.merge(context, writer);

            writer.flush();
            writer.close();
        } catch(Exception e) {
            logger.error("Unable to write message", e);
        }

    }
    
    private void writeController(int opcode, String messageName, String tangiblePath) {

        String className = messageName + "Command";
        
        String outFileName = controllerFilePath + className + ".java";
        File file = new File(outFileName);
        if(file.exists()) {
            logger.info("'" + className + "' already exists");
            return;
        }

        Template t = ve.getTemplate("/templates/commandcontroller.vm");

        VelocityContext context = new VelocityContext();

        context.put("packageName", controllerClassPath);
        context.put("tangibleClassPath", tangiblePath);
        context.put("messageClasspath", messageClassPath);
        context.put("messageName", messageName);
        context.put("className", className);
        context.put("controllerid", opcode);

        /* lets render a template */
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFileName)));

            if (!ve.evaluate(context, writer, t.getName(), "")) {
                throw new Exception("Failed to convert the template into class.");
            }

            t.merge(context, writer);

            writer.flush();
            writer.close();

            BufferedWriter controllerFileWriter = new BufferedWriter(new FileWriter(new File(controllerFile), true));
            controllerFileWriter.append(controllerClassPath + "." + className);
            controllerFileWriter.newLine();

            controllerFileWriter.flush();
            controllerFileWriter.close();
            
        } catch(Exception e) {
            logger.error("Unable to write controller", e);
        }
    }


}
