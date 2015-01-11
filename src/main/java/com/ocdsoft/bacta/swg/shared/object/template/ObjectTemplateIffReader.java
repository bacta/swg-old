package com.ocdsoft.bacta.swg.shared.object.template;

import com.ocdsoft.bacta.swg.shared.iff.IffReader;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkBuffer;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkBufferContext;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.object.template.param.TemplateBase;
import com.ocdsoft.bacta.swg.shared.object.template.param.TemplateBaseCollection;
import com.ocdsoft.bacta.swg.shared.object.template.param.TemplateBaseIffLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by crush on 3/4/14.
 */
public class ObjectTemplateIffReader implements IffReader<ObjectTemplate> {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
    private final ObjectTemplateList objectTemplateList;

    public ObjectTemplateIffReader(ObjectTemplateList objectTemplateList) {
        this.objectTemplateList = objectTemplateList;
    }

    @Override
    public ObjectTemplate read(ChunkReader chunkReader) {
        final ChunkBufferContext rootContext = chunkReader.nextChunk();

        if (rootContext == null)
            return null;

        /*
         * ObjectTemplates have the following layout:
         *  FORM:STOT
         *      FORM:DERV //optional
         *      FORM:0000 //required
         *      FORM:SHOT //optional
         *          FORM:DERV
         *          FORM:0000
         */

        logger.trace("Reading template <{}>.", chunkReader.getFileName());

        final ObjectTemplate objectTemplate = objectTemplateList.createObjectTemplate(
                rootContext.getGroupId(),
                chunkReader.getFileName());

        if (objectTemplate == null) {
            logger.error("Failed to create object template <{}> for type <{}>.",
                    chunkReader.getFileName(),
                    ChunkBuffer.getChunkName(rootContext.getGroupId()));
            return null;
        }

        readObjectTemplate(chunkReader, objectTemplate);

        return objectTemplate;
    }

    private void readObjectTemplate(ChunkReader chunkReader, ObjectTemplate objectTemplate) {
        ChunkBufferContext context = chunkReader.nextChunk();

        if (context == null)
            return;

        //If a DERV section exists, it will come before the version chunk.
        if (context.isFormType(ObjectTemplate.ID_DERV)) {
            readDerivesFrom(chunkReader, objectTemplate);
            context = chunkReader.nextChunk();
        }

        readVariables(chunkReader, objectTemplate);
        context = chunkReader.nextChunk();

        //Try to read the next object template if it exists.
        readObjectTemplate(chunkReader, objectTemplate);
    }

    private void readDerivesFrom(ChunkReader chunkReader, ObjectTemplate objectTemplate) {
        ChunkBufferContext context = chunkReader.nextChunk();

        final String derivesFromTemplateName = chunkReader.readNullTerminatedAscii();

        ObjectTemplate derivesFromTemplate = objectTemplateList.getObjectTemplate(derivesFromTemplateName);

        if (derivesFromTemplate == null) {
            logger.warn("Unable to fetch base template <{}> for <{}>.",
                    derivesFromTemplateName,
                    chunkReader.getFileName());

            return;
        }

        objectTemplate.setBaseData(derivesFromTemplate);

        //apply the variables from the base to the current template?
    }

    //When this method enter
    private void readVariables(ChunkReader chunkReader, ObjectTemplate objectTemplate) {
        ChunkBufferContext context = chunkReader.nextChunk();

        int count = chunkReader.readInt(); //Get the number of variables from the PCNT chunk

        for (int i = 0; i < count; i++)
            readVariable(chunkReader, objectTemplate);

        chunkReader.skipCurrentChunk();
    }

    private void readVariable(ChunkReader chunkReader, ObjectTemplate objectTemplate) {
        try {
            ChunkBufferContext context = chunkReader.nextChunk();

            final String variableName = chunkReader.readNullTerminatedAscii();

            Field field = getFieldByNameRecursively(objectTemplate.getClass(), variableName);

            if (field != null) {
                field.setAccessible(true);
                Object variableObject = field.get(objectTemplate);

                if (variableObject instanceof TemplateBaseCollection) {
                    TemplateBaseCollection collection = (TemplateBaseCollection) variableObject;
                    collection.setAppend(chunkReader.readBoolean());

                    int size = chunkReader.readInt();

                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            TemplateBase variableInstance = null;
                            //We need to find out the type of the contained object, so that we can add it to the container.
                            Type type = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

                            if (type instanceof ParameterizedType) {
                                ParameterizedType pt = (ParameterizedType) type;
                                variableInstance = (TemplateBase) ((Class) pt.getRawType()).newInstance();
                            } else {
                                variableInstance = (TemplateBase) ((Class) type).newInstance();
                            }

                            TemplateBaseIffLoader<TemplateBase> iffLoader = objectTemplateList.getTemplateBaseIffLoader(variableInstance.getClass());
                            iffLoader.load(variableInstance, chunkReader);
                            collection.add(variableInstance);
                        }

                        collection.setLoaded(true);
                    }
                } else if (variableObject instanceof TemplateBase[]) {
                    TemplateBase[] variableObjects = (TemplateBase[]) variableObject;

                    int size = chunkReader.readInt();

                    if (size > 0) {
                        if (size > variableObjects.length) {
                            logger.warn("Variable <{}> has {} elements but is defined as having {} elements. Extra values will be truncated.",
                                    variableName,
                                    size,
                                    variableObjects.length);

                            size = variableObjects.length;
                        }

                        TemplateBaseIffLoader<TemplateBase> iffLoader = objectTemplateList.getTemplateBaseIffLoader(variableObjects[0].getClass());

                        for (int i = 0; i < size; i++)
                            iffLoader.load(variableObjects[i], chunkReader);
                    }
                } else if (variableObject instanceof TemplateBase) {
                    TemplateBase templateBase = (TemplateBase) variableObject;
                    TemplateBaseIffLoader<TemplateBase> iffLoader = objectTemplateList.getTemplateBaseIffLoader(templateBase.getClass());
                    iffLoader.load(templateBase, chunkReader);
                } else {
                    logger.warn("Variable <{}> in object template <{}> is of unsupported type <{}>.",
                            variableName,
                            variableName.getClass().getName(),
                            chunkReader.getFileName());
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Field getFieldByNameRecursively(Class<?> classObject, final String fieldName) {
        try {
            return classObject.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            return getFieldByNameRecursively(classObject.getSuperclass(), fieldName);
        } catch (Exception ex) {
            return null;
        }
    }
}
