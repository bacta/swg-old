package com.ocdsoft.bacta.swg.template.definition;

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
            final File sourceDirectory = new File("C:/users/crush/downloads/swg-src_orig_noshare/whitengold/dsrc/sku.0/sys.server/compiled/game");
            final File destinationDirectory = new File("C:/users/crush/git/bacta/pre-cu-master/pre-cu/src/main/java/com/ocdsoft/bacta/swg/precu/object/template");

            final TemplateDefinitionCompiler compiler = new TemplateDefinitionCompiler();
            compiler.compile(sourceDirectory, destinationDirectory);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
