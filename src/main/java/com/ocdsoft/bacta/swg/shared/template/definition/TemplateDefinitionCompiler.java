package com.ocdsoft.bacta.swg.shared.template.definition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by crush on 4/18/2016.
 */
public class TemplateDefinitionCompiler {
    public void compile(final File sourceDirectory, final File destinationDirectory, final String templatePackage) throws IOException {
        final File[] files = sourceDirectory.listFiles((dir, name) -> {
            return name.endsWith(".tdf");
        });

        final TemplateDefinitionWriter writer = new TemplateDefinitionWriter(templatePackage);
        final String destinationPath = destinationDirectory.getAbsolutePath();

        Files.walk(Paths.get(sourceDirectory.getAbsolutePath()))
                .filter(p -> p.getFileName().toString().endsWith(".tdf"))
                .forEach(path -> {
                    try {
                        final File file = new File(path.toUri());

                        final TemplateDefinitionFile tdf = new TemplateDefinitionFile();
                        tdf.parse(file);

                        final File outputFile = new File(destinationPath, tdf.getTemplateName() + ".java");

                        try (final OutputStream outputStream = new FileOutputStream(outputFile)) {
                            writer.write(outputStream, tdf, tdf.getHighestVersion());
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }
}