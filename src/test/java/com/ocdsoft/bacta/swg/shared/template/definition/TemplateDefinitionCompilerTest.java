package com.ocdsoft.bacta.swg.shared.template.definition;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by crush on 4/20/2016.
 */
public class TemplateDefinitionCompilerTest {
    @Test
    public void shouldCompile() {
        try {
            //Shared
            final File sharedSourceDirectory = new File("C:\\Users\\crush\\Downloads\\swg-src_orig_noshare\\whitengold\\dsrc\\sku.0\\sys.shared\\compiled\\game");
            final File sharedDestinationDirectory = new File("C:/users/crush/git/bacta/pre-cu-master/pre-cu/src/main/java/com/ocdsoft/bacta/swg/precu/object/template/shared");
            final String sharedTemplatePackage = "com.ocdsoft.bacta.swg.precu.object.template.shared";

            //Server
            final File serverSourceDirectory = new File("C:\\Users\\crush\\Downloads\\swg-src_orig_noshare\\whitengold\\dsrc\\sku.0\\sys.server\\compiled\\game");
            final File serverDestinationDirectory = new File("C:/users/crush/git/bacta/pre-cu-master/pre-cu/src/main/java/com/ocdsoft/bacta/swg/precu/object/template/server");
            final String serverTemplatePackage = "com.ocdsoft.bacta.swg.precu.object.template.server";

            final TemplateDefinitionCompiler compiler = new TemplateDefinitionCompiler();
            compiler.compile(sharedSourceDirectory, sharedDestinationDirectory, sharedTemplatePackage);
            compiler.compile(serverSourceDirectory, serverDestinationDirectory, serverTemplatePackage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
