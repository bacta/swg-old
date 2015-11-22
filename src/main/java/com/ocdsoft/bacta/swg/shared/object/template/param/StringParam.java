package com.ocdsoft.bacta.swg.shared.object.template.param;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by crush on 11/21/2015.
 */
public class StringParam extends TemplateBase<String, String> {
    @Override
    public void loadFromIff(Iff iff) {
        final DataTypeId dataType = DataTypeId.forValue(iff.readByte());

        switch (dataType) {
            case SINGLE: {
                setValue(iff.readString());

                if (dataSingle.length() >= 7
                        && dataSingle.endsWith(".iff")
                        && dataSingle.indexOf('\\') != -1) {

                    dataSingle = dataSingle.replace('\\', '/');
                }

                loaded = true;
                break;
            }
            case WEIGHTED_LIST:
                setValue(new ArrayList<>());
                loadWeightedListFromIff(iff);
                break;
            case NONE:
                cleanData();
            case RANGE:
            case DIE_ROLL:
            default:
                Preconditions.checkState(false, "loaded unknown data type %s for template string param", dataType);
                break;
        }
    }

    @Override
    public void saveToIff(Iff iff) {
        iff.insertChunkData((byte) dataType.getValue());

        switch (dataType) {
            case SINGLE:
                iff.insertChunkData(dataSingle);
                break;
            case WEIGHTED_LIST:
                saveWeightedListToIff(iff);
                break;
            case NONE:
                break;
            case DIE_ROLL:
            case RANGE:
            default:
                Preconditions.checkArgument(false, "saving unknown data type %s for template string param.", dataType);
                break;
        }
    }

    @Override
    protected TemplateBase<String, String> createNewParam() {
        return new StringParam();
    }
}
