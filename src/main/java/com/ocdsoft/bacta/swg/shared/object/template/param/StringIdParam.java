package com.ocdsoft.bacta.swg.shared.object.template.param;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.localization.StringId;

import java.util.ArrayList;

/**
 * Created by crush on 11/21/2015.
 */
public class StringIdParam extends TemplateBase<StringIdParamData, StringIdParamData> {

    public StringId getStringIdValue() {
        if (dataType == DataTypeId.SINGLE) {

            return new StringId(dataSingle.table.getValue(),
                    dataSingle.index.getValue());

        } else if (dataType == DataTypeId.WEIGHTED_LIST) {

            int weight = random.nextInt(100) + 1;

            for (int i = 0; i < weightedList.size(); ++i) {
                weight -= weightedList.get(i).weight;

                if (weight <= 0) {
                    final StringIdParamData param = weightedList.get(i).value.getValue();
                    return new StringId(param.table.getValue(), param.index.getValue());
                }
            }

            Preconditions.checkState(false, "Weighted list does not equal 100.");
        }

        Preconditions.checkState(false, "INVALID type for string id param.");
        return StringId.INVALID;
    }

    @Override
    protected void cleanSingleParam() {
        dataSingle.table.cleanData();
        dataSingle.index.cleanData();
    }

    @Override
    public void loadFromIff(final Iff iff) {
        final DataTypeId dataType = DataTypeId.forValue(iff.readByte());

        switch (dataType) {
            case SINGLE: {
                final StringIdParamData data = new StringIdParamData();
                data.table.loadFromIff(iff);
                data.index.loadFromIff(iff);
                setValue(data);
                loaded = true;
                break;
            }
            case WEIGHTED_LIST: {
                setValue(new ArrayList<>());
                loadWeightedListFromIff(iff);
                break;
            }
            case NONE:
                cleanData();
                break;
            case RANGE:
            case DIE_ROLL:
            default:
                Preconditions.checkArgument(false, "loaded unknown data type %s for template stringId param.", dataType);
                break;
        }
    }

    @Override
    public void saveToIff(final Iff iff) {
        iff.insertChunkData((byte) dataType.getValue());

        switch (dataType) {
            case SINGLE:
                dataSingle.table.saveToIff(iff);
                dataSingle.index.saveToIff(iff);
                break;
            case WEIGHTED_LIST:
                saveWeightedListToIff(iff);
                break;
            case NONE:
                break;
            case RANGE:
            case DIE_ROLL:
            default:
                Preconditions.checkArgument(false, "saving unknown data type %s for template stringId param.", dataType);
                break;
        }
    }

    @Override
    protected TemplateBase<StringIdParamData, StringIdParamData> createNewParam() {
        return new StringIdParam();
    }
}
