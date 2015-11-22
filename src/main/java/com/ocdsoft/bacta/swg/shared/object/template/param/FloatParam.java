package com.ocdsoft.bacta.swg.shared.object.template.param;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by crush on 11/21/2015.
 */
public final class FloatParam extends TemplateBase<Float, Float> {
    private byte dataDeltaType; //if '+' or '-' then param is a delta on a derived template param.

    public byte getDeltaType() {
        return dataDeltaType;
    }

    public void setDeltaType(final byte type) {
        this.dataDeltaType = type;
    }

    public FloatParam() {
        dataDeltaType = ' ';
    }

    /**
     * Returns the minimum value that a getValue() can return. This function will fatal
     * if the data type is a weighted list.
     *
     * @return the minimum getValue() return value.
     */
    public float getMinValue() {
        switch (dataType) {
            case SINGLE:
                return dataSingle;
            case RANGE:
                Preconditions.checkNotNull(range);
                return range.minValue;
            case NONE:
            case DIE_ROLL:
            case WEIGHTED_LIST:
            default:
                Preconditions.checkState(false, "getting min value for FloatParam data type %s", dataType);
                break;
        }
        return 0.0f;
    }

    /**
     * Returns the maximum value that a getValue() can return. This function will fatal
     * if the data type is a weighted list.
     *
     * @return the maximum getValue() return value.
     */
    public float getMaxValue() {
        switch (dataType) {
            case SINGLE:
                return dataSingle;
            case RANGE:
                Preconditions.checkNotNull(range);
                return range.maxValue;
            case NONE:
            case DIE_ROLL:
            case WEIGHTED_LIST:
            default:
                Preconditions.checkState(false, "getting max value for FloatParam data type %s", dataType);
                break;
        }
        return 0.0f;
    }

    @Override
    public void loadFromIff(final Iff iff) {
        final DataTypeId dataType = DataTypeId.forValue(iff.readByte());
        dataDeltaType = iff.readByte();

        switch (dataType) {
            case SINGLE:
                setValue(iff.readFloat());
                loaded = true;
            case WEIGHTED_LIST:
                setValue(new ArrayList<>());
                loadWeightedListFromIff(iff);
                break;
            case RANGE: {
                float maxValue = iff.readFloat();
                float minValue = iff.readFloat();
                setValue(minValue, maxValue);
                loaded = true;
                break;
            }
            case NONE:
                cleanData();
                break;
            case DIE_ROLL:
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
            case RANGE:
                Preconditions.checkNotNull(range);
                iff.insertChunkData(range.minValue);
                iff.insertChunkData(range.maxValue);
                break;
            case NONE:
                break;
            case DIE_ROLL:
            default:
                Preconditions.checkArgument(false, "saving unknown data type %s for template float param.", dataType);
                break;
        }
    }

    @Override
    protected TemplateBase<Float, Float> createNewParam() {
        return new FloatParam();
    }

    @Override
    protected Float getRange() {
        Preconditions.checkState(dataType == DataTypeId.RANGE, "getRange on non-range float param.");
        Preconditions.checkNotNull(range);

        float minValue = range.minValue.floatValue();
        float maxValue = range.maxValue.floatValue();

        return random.nextFloat() * (maxValue - minValue) + minValue;
    }
}
