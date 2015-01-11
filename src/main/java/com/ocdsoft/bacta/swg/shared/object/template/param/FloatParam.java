package com.ocdsoft.bacta.swg.shared.object.template.param;

/**
 * Created by crush on 3/4/14.
 */
public final class FloatParam extends TemplateBase<FloatParam> {
    private float dataSingle;
    protected byte dataDeltaType;

    public byte getDeltaType() { return dataDeltaType; }

    public float getValue() {
        float result = 0.0f;

        switch (this.dataType) {
            case DataTypeId.Single:
                result = getSingle();
                break;
            case DataTypeId.WeightedList:
                int rand = random.nextInt(100) + 1;
                WeightedValueList list = (WeightedValueList)this.data;
                //TODO: Figure this out...
                logger.debug("Attempting to get value of weighted value list, but it is currently unimplemented.");
                break;
            case DataTypeId.Range:
                result = getRange();
                break;
            case DataTypeId.DieRoll:
                result = getDieRoll();
                break;
            default:
                logger.debug("Unknown data type <{}>.", this.dataType);
                break;
        }

        return result;
    }

    public float getMinValue() {
        if (this.dataType == DataTypeId.Single)
            return this.dataSingle;

        if (this.dataType != DataTypeId.Range) {
            logger.debug("getMinValue was called on non-range data type <{}>.", this.dataType);
            return 0.0f;
        }

        return ((Range)this.data).minValue;
    }

    public float getMaxValue() {
        if (this.dataType == DataTypeId.Single)
            return this.dataSingle;

        if (this.dataType != DataTypeId.Range) {
            logger.debug("getMaxValue was called on non-range data type <{}>.", this.dataType);
            return 0.0f;
        }

        return ((Range)this.data).maxValue;
    }

    public float getSingle() {
        return dataSingle;
    }

    public float getRange() {
        if (this.dataType != DataTypeId.Range) {
            logger.debug("getRange was called on non-range data type <{}>.", this.dataType);
            return 0.0f;
        }

        Range range = (Range)this.data;
        return random.nextFloat() * (range.maxValue - range.minValue) + range.minValue;
    }

    public float getDieRoll() {
        throw new UnsupportedOperationException();
    }

    public void setValue(float value) {
        cleanData();
        this.dataSingle = value;
        this.dataType = DataTypeId.Single;
        this.loaded = true;
    }

    public void setValue(WeightedValueList list) {
        cleanData();
        this.data = list;
        this.dataType = DataTypeId.WeightedList;
        this.loaded = true;
    }

    public void setValue(float minValue, float maxValue) {
        cleanData();
        Range range = new Range();
        range.minValue = minValue;
        range.maxValue = maxValue;
        this.data = range;
        this.dataType = DataTypeId.Range;
        this.loaded = true;
    }

    @Override
    public FloatParam createNewParam() { return new FloatParam(); }

    @Override
    public FloatParam createDeepCopy() {
        FloatParam param = createNewParam();
        param.dataType = this.dataType;
        param.dataDeltaType = this.dataDeltaType;
        param.loaded = this.loaded;

        switch (this.dataType) {
            case DataTypeId.None:
                break;
            case DataTypeId.Single:
                param.dataSingle = this.dataSingle;
                break;
            case DataTypeId.WeightedList:
                WeightedValueList thisList = (WeightedValueList)data;
                WeightedValueList thatList = new WeightedValueList(thisList.size());

                for (WeightedValue weightedValue : thisList)
                    thatList.add(new WeightedValue(weightedValue));

                param.data = thatList;
                break;
            case DataTypeId.Range:
                Range thisRange = (Range)this.data;
                Range thatRange = new Range();
                thatRange.minValue = thisRange.minValue;
                thatRange.maxValue = thisRange.maxValue;
                param.data = thatRange;
                break;
            case DataTypeId.DieRoll:
                break;
            default:
                logger.debug("Attempted to create deep copy for unknown data type <{}>.", this.dataType);
                break;
        }

        return param;
    }

    private static final class Range implements DataType {
        private float minValue;
        private float maxValue;
    }
}
