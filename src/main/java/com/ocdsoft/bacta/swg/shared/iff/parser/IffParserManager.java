package com.ocdsoft.bacta.swg.shared.iff.parser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkBuffer;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Singleton
public final class IffParserManager {

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private final Map<String, TypeParser<?>> typeParserMap = new HashMap<>();
    private final Map<Integer, HashMap<Integer, IffParser>> parseMap = new HashMap<>();

    @Inject
    public IffParserManager() {
        loadTypeParsers();
        loadFormParsers();

    }

    @SuppressWarnings("rawtypes")
    private void loadTypeParsers() {
        Reflections reflections = new Reflections("com.ocdsoft.bacta.shared.iff.parser.type");

        Set<Class<? extends TypeParser>> parserSet = reflections.getSubTypesOf(TypeParser.class);

        Iterator<Class<? extends TypeParser>> parserIter = parserSet.iterator();
        while (parserIter.hasNext()) {

            Class<? extends TypeParser> parserClass = parserIter.next();

            try {
                String typeName = parserClass.getSimpleName().replace("Parser", "");
                typeParserMap.put(typeName, parserClass.newInstance());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    private void loadFormParsers() {

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getJsonFactory();
        JsonParser parser = null;
        InputStream stream = null;

        Reflections reflections = new Reflections("iff.form", new ResourcesScanner());
        Set<String> parsers = reflections.getResources(Pattern.compile(".*\\.json"));

        for (String parserPath : parsers) {
            try {
                stream = getClass().getClassLoader().getResourceAsStream(parserPath);

                parser = factory.createJsonParser(stream);

                @SuppressWarnings("unused")
                JsonToken token = parser.nextToken(); // JsonToken.START_OBJECT
                token = parser.nextToken(); // Form Name

                int formName = ChunkBuffer.createChunkId(parser.getText());

                token = parser.nextToken(); // Start Array

                token = parser.nextToken(); // ArrayObject

                // For each version object
                while ((token = parser.nextToken()) == JsonToken.FIELD_NAME) {

                    //IffParser parsingData = new IffParser(parserPath);

                    int version = ChunkBuffer.createChunkId(parser.getText());

                    token = parser.nextToken(); // JsonToken.START_OBJECT

                    while ((token = parser.nextToken()) != JsonToken.END_OBJECT) {
                        String fieldName = parser.getText().toLowerCase(); // JsonToken.FIELD_NAME

                        token = parser.nextToken();

                        String parserType = parser.getText(); // JsonToken.VALUE_STRING

                        TypeParser<?> typeParser = typeParserMap.get(parserType);

                        if (typeParser != null) {
                            //parsingData.add(fieldName, typeParser);
                        } else {
                            logger.error("Unable to get parser: " + parserType);
                        }
                    }

                    HashMap<Integer, IffParser> versions = parseMap.get(formName);
                    if (versions == null) {
                        versions = new HashMap<Integer, IffParser>();
                        parseMap.put(formName, versions);
                    }

                    //versions.put(version, parsingData);
                }

            } catch (Exception e) {
                logger.error("", e);
            }
        }

    }

    /*

    public IffParser getParser(IffFile file) {

        FormChunk rootForm = null;//(FormChunk) file.getRootChunk();

        HashMap<Integer, IffParser> versions = parseMap.get(rootForm.getChunkType());
        if (versions == null) {
            logger.error("Missing parser for " + Chunk.getChunkName(rootForm.getChunkType()));
            return null;
        }

        Chunk version = null;//rootForm.nextChunk();

        return versions.get(version.getChunkType());
    }

    public IffParser getParser(FormChunk form) {

        HashMap<Integer, IffParser> versions = parseMap.get(form.getChunkType());
        Chunk version = null;//form.nextChunk();

        return versions.get(version.getChunkType());
    }*/
}
