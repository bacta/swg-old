package com.ocdsoft.bacta.swg.shared.object.template.param;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by crush on 11/21/2015.
 */
public final class BoolParam extends TemplateBase<Boolean, Boolean> {
    private byte dataDeltaType; //if '+' or '-' then param is a delta on a derived template param.

    public byte getDeltaType() {
        return dataDeltaType;
    }

    public void setDeltaType(final byte type) {
        this.dataDeltaType = type;
    }

    public BoolParam() {
        dataDeltaType = ' ';
    }

    @Override
    public void loadFromIff(final Iff iff) {
        final DataTypeId dataType = DataTypeId.forValue(iff.readByte());
        dataDeltaType = iff.readByte();

        switch (dataType) {
            case SINGLE:
                setValue(iff.readBoolean());
                loaded = true;
            case WEIGHTED_LIST:
                setValue(new ArrayList<>());
                loadWeightedListFromIff(iff);
                break;
            case NONE:
                cleanData();
                break;
            case DIE_ROLL:
            case RANGE:
            default:
                Preconditions.checkArgument(false, "loaded unknown data type %s for template float param.", dataType);
                break;
        }
    }

    @Override
    public void saveToIff(final Iff iff) {
        iff.insertChunkData((byte) dataType.getValue());
        iff.insertChunkData(dataDeltaType);

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
                Preconditions.checkArgument(false, "saving unknown data type %s for template float param.", dataType);
                break;
        }
    }

    @Override
    protected TemplateBase<Boolean, Boolean> createNewParam() {
        return new BoolParam();
    }
}
