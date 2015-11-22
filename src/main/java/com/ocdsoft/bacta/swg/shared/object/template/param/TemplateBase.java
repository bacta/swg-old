package com.ocdsoft.bacta.swg.shared.object.template.param;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by crush on 3/4/14.
 */
public abstract class TemplateBase<DataType, ReturnType> {
    protected static final Random random = new Random();

    public class WeightedValue {
        public TemplateBase<DataType, ReturnType> value;
        public int weight;
    }

    public class Range {
        public DataType minValue;
        public DataType maxValue;
    }

    public class DieRoll {
        public DataType numDice;
        public DataType dieSides;
        public DataType base;
    }

    enum DataTypeId {
        NONE(0),
        SINGLE(1),
        WEIGHTED_LIST(2),
        RANGE(3),
        DIE_ROLL(4);

        private static final DataTypeId[] values = new DataTypeId[]{
                NONE, SINGLE, WEIGHTED_LIST, RANGE, DIE_ROLL
        };

        @Getter
        private final int value;

        DataTypeId(int value) {
            this.value = value;
        }

        public static DataTypeId forValue(int value) {
            Preconditions.checkPositionIndex(value, values.length);
            return values[value];
        }
    }

    protected DataTypeId dataType;
    protected DataType dataSingle;
    protected Range range;
    protected DieRoll dieRoll;
    protected ArrayList<WeightedValue> weightedList; //Enforce ArrayList so we can trimToSize after loading.
    protected boolean loaded;

    public DataTypeId getType() {
        return dataType;
    }

    public void cleanData() {
        if (weightedList != null)
            weightedList.clear();

        dataType = DataTypeId.NONE;
        loaded = false;
    }

    public abstract void loadFromIff(final Iff iff);

    public abstract void saveToIff(final Iff iff);

    public ReturnType getValue() {
        switch (dataType) {
            case SINGLE:
                return getSingle();
            case WEIGHTED_LIST: {
                int weight = random.nextInt(100) + 1;

                for (int i = 0; i < weightedList.size(); ++i) {
                    weight -= weightedList.get(i).weight;

                    if (weight <= 0) {
                        return weightedList.get(i).value.getValue();
                    }
                }

                Preconditions.checkArgument(false, "Weighted list does not equal 100.");
                break;
            }
            case RANGE:
                return getRange();
            case DIE_ROLL:
                return getDieRoll();
            case NONE:
            default:
                Preconditions.checkArgument(false, "Unknown data type %s for template param.", dataType);
                break;
        }

        return null; //TODO: Not sure if this will work.
    }

    public Range getRawRange() {
        if (dataType == DataTypeId.RANGE)
            return range;

        return null;
    }

    public DieRoll getRawDieRoll() {
        if (dataType == DataTypeId.DIE_ROLL)
            return dieRoll;

        return null;
    }

    public List<WeightedValue> getRawWeightedList() {
        if (dataType == DataTypeId.WEIGHTED_LIST)
            return weightedList;

        return null;
    }

    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Does cleanup for a single-value parameter.
     */
    protected void cleanSingleParam() {
    }

    /**
     * Loads a weighted list from an iff file.
     *
     * @param iff the file to load from
     */
    protected void loadWeightedListFromIff(final Iff iff) {
        Preconditions.checkNotNull(weightedList);

        int count = iff.readInt();

        if (count <= 0)
            return;

        for (int i = 0; i < count; ++i) {
            final WeightedValue weightedValue = new WeightedValue();
            weightedValue.weight = iff.readInt();
            weightedValue.value = createNewParam();
            weightedValue.value.loadFromIff(iff);
            weightedList.add(weightedValue);
        }

        weightedList.trimToSize();

        loaded = true;
    }

    /**
     * Saves a weighted list to an iff file.
     *
     * @param iff the file to save to
     */
    protected void saveWeightedListToIff(final Iff iff) {
        Preconditions.checkNotNull(weightedList);

        int size = weightedList.size();
        iff.insertChunkData(size);

        for (int i = 0; i < size; ++i) {
            final WeightedValue weightedValue = weightedList.get(i);

            iff.insertChunkData(weightedValue.weight);
            weightedValue.value.saveToIff(iff);
        }
    }

    protected abstract TemplateBase<DataType, ReturnType> createNewParam();

    protected ReturnType getSingle() {
        return (ReturnType) dataSingle;
    }

    protected ReturnType getRange() {
        Preconditions.checkArgument(false, "getRange is not supported.");
        return null;
    }

    protected ReturnType getDieRoll() {
        Preconditions.checkArgument(false, "getDieRoll is not supported.");
        return null;
    }

    public void setValue(final DataType value) {
        cleanData();
        dataType = DataTypeId.SINGLE;
        dataSingle = value;
        loaded = true;
    }

    public void setValue(final ArrayList<WeightedValue> list) {
        Preconditions.checkNotNull(list);

        cleanData();
        dataType = DataTypeId.WEIGHTED_LIST;
        weightedList = list;
        loaded = true;
    }

    protected void setValue(final DataType minValue, final DataType maxValue) {
        cleanData();
        dataType = DataTypeId.RANGE;
        range = new Range();
        range.minValue = minValue;
        range.maxValue = maxValue;
        loaded = true;
    }

    protected void setValue(final DataType numDice, final DataType dieSides, final DataType base) {
        cleanData();
        dataType = DataTypeId.DIE_ROLL;
        dieRoll = new DieRoll();
        dieRoll.numDice = numDice;
        dieRoll.dieSides = dieSides;
        dieRoll.base = base;
        loaded = true;
    }


}
