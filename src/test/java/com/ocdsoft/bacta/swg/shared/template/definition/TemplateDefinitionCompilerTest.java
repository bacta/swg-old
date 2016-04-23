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
            final File sourceDirectory = new File("C:\\Users\\crush\\Downloads\\swg-src_orig_noshare\\whitengold\\dsrc\\sku.0\\sys.shared\\compiled\\game");
            final File destinationDirectory = new File("C:/users/crush/git/bacta/pre-cu-master/pre-cu/src/main/java/com/ocdsoft/bacta/swg/precu/object/template/shared");
            final String templatePackage = "com.ocdsoft.bacta.swg.precu.object.template.shared";

            final TemplateDefinitionCompiler compiler = new TemplateDefinitionCompiler();
            compiler.compile(sourceDirectory, destinationDirectory, templatePackage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
