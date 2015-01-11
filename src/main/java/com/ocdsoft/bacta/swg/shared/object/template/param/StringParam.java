package com.ocdsoft.bacta.swg.shared.object.template.param;

public final class StringParam extends TemplateBase<StringParam> {
    protected String dataSingle;

    public String getValue() {
        String result = "";

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

    public String getSingle() {
        return dataSingle;
    }

    public String getRange() {
        throw new UnsupportedOperationException();
    }

    public String getDieRoll() {
        throw new UnsupportedOperationException();
    }

    public void setValue(String value) {
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
    public StringParam createNewParam() { return new StringParam(); }

    @Override
    public StringParam createDeepCopy() {
        StringParam param = createNewParam();
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
