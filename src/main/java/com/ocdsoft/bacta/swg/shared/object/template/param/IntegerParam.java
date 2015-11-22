package com.ocdsoft.bacta.swg.shared.object.template.param;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;

import java.util.ArrayList;

/**
 * Created by crush on 11/21/2015.
 */
public final class IntegerParam extends TemplateBase<Integer, Integer> {
    private byte dataDeltaType; //if '+' or '-' then param is a delta on a derived template param.

    public byte getDeltaType() {
        return dataDeltaType;
    }

    public void setDeltaType(final byte type) {
        this.dataDeltaType = type;
    }

    public IntegerParam() {
        dataDeltaType = ' ';
    }

    /**
     * Returns the minimum value that a getValue() can return. This function will fatal
     * if the data type is a weighted list.
     *
     * @return the minimum getValue() return value.
     */
    public int getMinValue() {
        switch (dataType) {
            case SINGLE:
                return dataSingle.intValue();
            case RANGE:
                Preconditions.checkNotNull(range);
                return range.minValue.intValue();
            case DIE_ROLL:
                Preconditions.checkNotNull(dieRoll);
                return dieRoll.base.intValue() + dieRoll.numDice.intValue();
            case NONE:
            case WEIGHTED_LIST:
            default:
                Preconditions.checkState(false, "getting min value for IntegerParam data type %s", dataType);
                break;
        }
        return 0;
    }

    /**
     * Returns the maximum value that a getValue() can return. This function will fatal
     * if the data type is a weighted list.
     *
     * @return the maximum getValue() return value.
     */
    public int getMaxValue() {
        switch (dataType) {
            case SINGLE:
                return dataSingle.intValue();
            case RANGE:
                Preconditions.checkNotNull(range);
                return range.maxValue.intValue();
            case DIE_ROLL:
                Preconditions.checkNotNull(dieRoll);
                return dieRoll.base.intValue() + dieRoll.numDice.intValue() * dieRoll.dieSides.intValue();
            case NONE:
            case WEIGHTED_LIST:
            default:
                Preconditions.checkState(false, "getting max value for IntegerParam data type %s", dataType);
                break;
        }
        return 0;
    }

    @Override
    public void loadFromIff(final Iff iff) {
        final DataTypeId dataType = DataTypeId.forValue(iff.readByte());
        dataDeltaType = iff.readByte();

        switch (dataType) {
            case SINGLE:
                setValue(iff.readInt());
                loaded = true;
            case WEIGHTED_LIST:
                setValue(new ArrayList<>());
                loadWeightedListFromIff(iff);
                break;
            case RANGE: {
                int maxValue = iff.readInt();
                int minValue = iff.readInt();
                setValue(minValue, maxValue);
                loaded = true;
                break;
            }
            case DIE_ROLL: {
                int numDice = iff.readInt();
                int dieSides = iff.readInt();
                int base = iff.readInt();
                setValue(numDice, dieSides, base);
                loaded = true;
                break;
            }
            case NONE:
                cleanData();
                break;
            default:
                Preconditions.checkArgument(false, "loaded unknown data type %s for template int param.", dataType);
                break;
        }
    }

    @Override
    public void saveToIff(final Iff iff) {
        iff.insertChunkData((byte) dataType.getValue());
        iff.insertChunkData(dataDeltaType);

        switch (dataType) {
            case SINGLE:
                iff.insertChunkData(dataSingle.intValue());
                break;
            case WEIGHTED_LIST:
                saveWeightedListToIff(iff);
                break;
            case RANGE:
                Preconditions.checkNotNull(range);
                iff.insertChunkData(range.minValue);
                iff.insertChunkData(range.maxValue);
                break;
            case DIE_ROLL:
                Preconditions.checkNotNull(dieRoll);
                iff.insertChunkData(dieRoll.numDice);
                iff.insertChunkData(dieRoll.dieSides);
                iff.insertChunkData(dieRoll.base);
                break;
            case NONE:
                break;
            default:
                Preconditions.checkArgument(false, "saving unknown data type %s for template int param.", dataType);
                break;
        }
    }

    @Override
    protected TemplateBase<Integer, Integer> createNewParam() {
        return new IntegerParam();
    }

    @Override
    protected Integer getRange() {
        Preconditions.checkState(dataType == DataTypeId.RANGE, "getRange on non-range integer param.");
        Preconditions.checkNotNull(range);

        return random.nextInt(range.maxValue - range.minValue) + range.minValue;
    }

    @Override
    protected Integer getDieRoll() {
        Preconditions.checkState(dataType == DataTypeId.DIE_ROLL, "getDieRoll on non-die integer param.");
        Preconditions.checkNotNull(dieRoll);

        Integer result = dieRoll.base;

        int numDice = dieRoll.numDice.intValue();
        int dieSides = dieRoll.dieSides.intValue();

        for (int i = 0; i < numDice; ++i)
            result += random.nextInt(dieSides) + 1;

        return result;
    }
}
