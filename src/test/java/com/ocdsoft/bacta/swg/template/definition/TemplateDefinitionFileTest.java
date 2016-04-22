package com.ocdsoft.bacta.swg.template.definition;

import com.ocdsoft.bacta.swg.datatable.DataTableTest;
import org.junit.Test;

import java.io.File;

/**
 * Created by crush on 4/18/2016.
 */
public class TemplateDefinitionFileTest {
    private static final String resourcesPath = new File(DataTableTest.class.getResource("/").getFile()).getAbsolutePath();

    @Test
    public void shouldParseFile() throws Exception {
        final String filename = "creature_object_template";
        final File templateFile = new File(resourcesPath, filename + ".tdf");
        final TemplateDefinitionFile objectTemplateTdf = new TemplateDefinitionFile();
        objectTemplateTdf.parse(templateFile);
    }
}
