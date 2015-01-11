package com.ocdsoft.bacta.swg.shared.object.template.param;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 8/20/2014.
 */
public class IntegerParamIffLoader implements TemplateBaseIffLoader<IntegerParam> {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Override
    public void load(IntegerParam param, ChunkReader chunkReader) {
        byte id = chunkReader.readByte();

        param.dataDeltaType = chunkReader.readByte();

        switch (id) {
            case 1:
                param.setValue(chunkReader.readInt());
                break;
            case 2:
                param.setValue(new TemplateBase.WeightedValueList());
                loadWeightedListFromIff(param, chunkReader);
                break;
            case 3:
                param.setValue(chunkReader.readInt(), chunkReader.readInt());
                break;
            case 4:
                param.setValue(chunkReader.readInt(), chunkReader.readInt(), chunkReader.readInt());
                break;
            case 0:
                param.cleanData();
                break;
            default:
                logger.debug("Attempted to load an unknown data type <{}>.", id);
        }
    }

    private void loadWeightedListFromIff(IntegerParam param, ChunkReader chunkReader) {
        if (param.getDataType() != TemplateBase.DataTypeId.WeightedList) {
            logger.debug("Unable to load weighted list for non-weighted list type <{}>.", param.dataType);
            return;
        }

        TemplateBase.WeightedValueList list = (TemplateBase.WeightedValueList)param.data;

        int size = chunkReader.readInt();

        for (int i = 0; i < size; i++) {
            TemplateBase.WeightedValue weightedValue = new TemplateBase.WeightedValue();
            weightedValue.weight = chunkReader.readInt();
            weightedValue.value = param.createNewParam();
            load((IntegerParam)weightedValue.value, chunkReader);

            list.add(weightedValue);
        }

        list.trimToSize();

        param.loaded = true;
    }
}
