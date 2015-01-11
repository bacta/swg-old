package com.ocdsoft.bacta.swg.shared.object.template.param;

/**
 * Created by crush on 3/4/14.
 */
public final class BoolParam extends TemplateBase<BoolParam> {
    private boolean dataSingle;

    public boolean getValue() {
        boolean result = false;

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

    public boolean getSingle() {
        return dataSingle;
    }

    public boolean getRange() {
        throw new UnsupportedOperationException();
    }

    public boolean getDieRoll() {
        throw new UnsupportedOperationException();
    }

    public void setValue(boolean value) {
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

    @Override
    public BoolParam createNewParam() { return new BoolParam(); }

    @Override
    public BoolParam createDeepCopy() {
        BoolParam param = createNewParam();
        param.dataType = this.dataType;
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
            default:
                logger.debug("Attempted to create deep copy for unknown data type <{}>.", this.dataType);
                break;
        }

        return param;
    }
}