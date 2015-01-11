package com.ocdsoft.bacta.swg.shared.object.template.param;

/**
 * Created by crush on 3/4/14.
 */
public final class IntegerParam extends TemplateBase<IntegerParam> {
    private int dataSingle;
    protected byte dataDeltaType;

    public byte getDeltaType() { return dataDeltaType; }

    public int getValue() {
        int result = 0;

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

    public int getMinValue() {
        if (this.dataType == DataTypeId.Single)
            return this.dataSingle;

        if (this.dataType != DataTypeId.Range) {
            logger.debug("getMinValue was called on non-range data type <{}>.", this.dataType);
            return 0;
        }

        return ((Range)this.data).minValue;
    }

    public int getMaxValue() {
        if (this.dataType == DataTypeId.Single)
            return this.dataSingle;

        if (this.dataType != DataTypeId.Range) {
            logger.debug("getMaxValue was called on non-range data type <{}>.", this.dataType);
            return 0;
        }

        return ((Range)this.data).maxValue;
    }

    public int getSingle() {
        return dataSingle;
    }

    public int getRange() {
        if (this.dataType != DataTypeId.Range) {
            logger.debug("getRange was called on non-range data type <{}>.", this.dataType);
            return 0;
        }

        Range range = (Range)this.data;
        return (int)(random.nextFloat() * (range.maxValue - range.minValue) + range.minValue);
    }

    public int getDieRoll() {
        if (this.dataType != DataTypeId.DieRoll) {
            logger.debug("getDieRoll was called on non-range data type <{}>.", this.dataType);
            return 0;
        }

        DieRoll dieRoll = (DieRoll)this.data;

        int result = dieRoll.base;

        for (int i = 0; i < dieRoll.numDice; ++i)
            result += (random.nextInt() % dieRoll.dieSides) + 1;

        return result;
    }

    public void setValue(int value) {
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

    public void setValue(int minValue, int maxValue) {
        cleanData();
        Range range = new Range();
        range.minValue = minValue;
        range.maxValue = maxValue;
        this.data = range;
        this.dataType = DataTypeId.Range;
        this.loaded = true;
    }

    public void setValue(int numDice, int dieSides, int base) {
        cleanData();
        DieRoll dieRoll = new DieRoll();
        dieRoll.numDice = numDice;
        dieRoll.dieSides = dieSides;
        dieRoll.base = base;
        this.data = dieRoll;
        this.dataType = DataTypeId.DieRoll;
        this.loaded = true;
    }

    @Override
    public IntegerParam createNewParam() { return new IntegerParam(); }

    @Override
    public IntegerParam createDeepCopy() {
        IntegerParam param = createNewParam();
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
                DieRoll thisDieRoll = (DieRoll)this.data;
                DieRoll thatDieRoll = new DieRoll();
                thatDieRoll.numDice = thisDieRoll.numDice;
                thatDieRoll.dieSides = thisDieRoll.dieSides;
                thatDieRoll.base = thisDieRoll.base;
                param.data = thatDieRoll;
                break;
            default:
                logger.debug("Attempted to create deep copy for unknown data type <{}>.", this.dataType);
                break;
        }

        return param;
    }

    private static final class Range implements DataType {
        private int minValue;
        private int maxValue;
    }

    private static final class DieRoll implements DataType {
        private int numDice;
        private int dieSides;
        private int base;
    }
}
