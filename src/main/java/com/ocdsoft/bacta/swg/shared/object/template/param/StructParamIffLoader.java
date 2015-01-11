package com.ocdsoft.bacta.swg.shared.object.template.param;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkBuffer;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.object.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.object.template.ObjectTemplateList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 8/20/2014.
 */
public class StructParamIffLoader implements TemplateBaseIffLoader<StructParam<ObjectTemplate>> {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
    private final ObjectTemplateList objectTemplateList;

    public StructParamIffLoader(ObjectTemplateList objectTemplateList) {
        this.objectTemplateList = objectTemplateList;
    }

    @Override
    public void load(StructParam<ObjectTemplate> param, ChunkReader chunkReader) {
        byte id = chunkReader.readByte();

        switch (id) {
            case 1:
                int bindingId = chunkReader.readInt();

                chunkReader.nextChunk();

                ObjectTemplate objectTemplate = objectTemplateList.createObjectTemplate(bindingId, "");

                if (objectTemplate == null) {
                    logger.error("Unable to create object template with binding id <{}>.", ChunkBuffer.getChunkName(bindingId));
                    chunkReader.closeChunk();
                    chunkReader.nextChunk();
                    return;
                }

                //objectTemplate.loadFromIff(chunkReader);
                chunkReader.closeChunk();
                param.setValue(objectTemplate);
                chunkReader.nextChunk();
                break;
            case 2:
                param.setValue(new TemplateBase.WeightedValueList());
                loadWeightedListFromIff(param, chunkReader);
                break;
            case 0:
                param.cleanData();
                break;
            default:
                logger.debug("Attempted to load an unknown data type <{}>.", id);
        }
    }

    private void loadWeightedListFromIff(StructParam<ObjectTemplate> param, ChunkReader chunkReader) {
        if (param.getDataType() != TemplateBase.DataTypeId.WeightedList) {
            logger.debug("Unable to load weighted list for non-weighted list type <{}>.", param.dataType);
            return;
        }

        logger.error("Attempting to load weighted list");

        TemplateBase.WeightedValueList list = (TemplateBase.WeightedValueList)param.data;

        int size = chunkReader.readInt();

        chunkReader.nextChunk();

        for (int i = 0; i < size; i++) {
            TemplateBase.WeightedValue weightedValue = new TemplateBase.WeightedValue();
            weightedValue.weight = chunkReader.readInt();
            weightedValue.value = param.createNewParam();
            //load((StructParam<ObjectTemplate>) weightedValue.value, chunkReader);

            list.add(weightedValue);
        }

        list.trimToSize();

        param.loaded = true;
    }
}
