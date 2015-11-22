package com.ocdsoft.bacta.swg.shared.object.template.param;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by crush on 11/21/2015.
 */
public final class VectorParam extends TemplateBase<VectorParamData, VectorParamData> {
    @Override
    public void cleanSingleParam() {
        dataSingle.x.cleanData();
        dataSingle.y.cleanData();
        dataSingle.z.cleanData();
        dataSingle.radius.cleanData();
    }

    @Override
    public void loadFromIff(Iff iff) {
        final DataTypeId dataType = DataTypeId.forValue(iff.readByte());

        switch (dataType) {
            case SINGLE:
                if (dataType == DataTypeId.WEIGHTED_LIST)
                    cleanData();

                this.dataType = DataTypeId.SINGLE;
                dataSingle.ignoreY = iff.readBoolean();
                dataSingle.x.loadFromIff(iff);
                if (!dataSingle.ignoreY)
                    dataSingle.y.loadFromIff(iff);
                dataSingle.z.loadFromIff(iff);
                dataSingle.radius.loadFromIff(iff);
                loaded = true;
                break;
            case WEIGHTED_LIST:
                setValue(new ArrayList<>());
                loadWeightedListFromIff(iff);
                break;
            case NONE:
                cleanData();
            case RANGE:
            case DIE_ROLL:
            default:
                Preconditions.checkArgument(false, "loaded unknown data type %s for template vector param.", dataType);
                break;
        }
    }

    @Override
    public void saveToIff(Iff iff) {
        iff.insertChunkData((byte) dataType.getValue());

        switch (dataType) {
            case SINGLE:
                iff.insertChunkData(dataSingle.ignoreY);
                dataSingle.x.saveToIff(iff);
                if (!dataSingle.ignoreY)
                    dataSingle.y.saveToIff(iff);
                dataSingle.z.saveToIff(iff);
                dataSingle.radius.saveToIff(iff);
                break;
            case WEIGHTED_LIST:
                saveWeightedListToIff(iff);
                break;
            case NONE:
                break;
            case RANGE:
            case DIE_ROLL:
            default:
                Preconditions.checkArgument(false, "saving unknown data type %s for template vector param.", dataType);
                break;
        }
    }

    @Override
    protected TemplateBase<VectorParamData, VectorParamData> createNewParam() {
        return new VectorParam();
    }
}
