package com.ocdsoft.bacta.swg.utility;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.foundation.DataResourceList;
import com.ocdsoft.bacta.swg.foundation.Tag;
import com.ocdsoft.bacta.swg.template.ObjectTemplate;

/**
 * Created by crush on 4/19/2016.
 */
public class StructParam<StructType extends ObjectTemplate> extends TemplateBase<StructType, StructType> {

    public boolean isInitialized() {
        return dataType != DataTypeId.NONE;
    }

    @Override
    protected TemplateBase<StructType, StructType> createNewParam() {
        return new StructParam<>();
    }

    @Override
    public void cleanSingleParam() {
        dataSingle = null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadFromIff(final DataResourceList<ObjectTemplate> resourceList, final Iff iff) {
        final DataTypeId dataType = DataTypeId.from(iff.readByte());

        switch (dataType) {
            case SINGLE:
                final int id = iff.readInt();
                final ObjectTemplate structTemplate = resourceList.fetch(id);
                Preconditions.checkNotNull(structTemplate);
                iff.exitChunk();
                structTemplate.loadFromIff(iff);
                iff.enterChunk();
                //This is kind of dangerous...but what can you do...
                setValue((StructType) structTemplate);
                loaded = true;
                break;
            case WEIGHTED_LIST:
                setValue(new WeightedList());
                loadWeightedListFromIff(resourceList, iff);
                break;
            case NONE:
                cleanData();
                break;
            case RANGE:
            case DIE_ROLL:
            default:
                break;
        }
    }

    @Override
    public void saveToIff(final DataResourceList<ObjectTemplate> resourceList, final Iff iff) {
        iff.insertChunkData(dataType.value);

        switch (dataType) {
            case SINGLE:
                iff.insertChunkData(dataSingle.getId());
                iff.exitChunk();
                dataSingle.saveToIff(iff);
                iff.enterChunk(Tag.TAG_XXXX);
                break;
            case WEIGHTED_LIST:
                saveWeightedListToIff(resourceList, iff);
                break;
            case RANGE:
            case DIE_ROLL:
            default:
                break;
        }
    }
}
