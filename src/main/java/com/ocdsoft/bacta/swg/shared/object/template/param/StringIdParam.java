package com.ocdsoft.bacta.swg.shared.object.template.param;

import com.ocdsoft.bacta.swg.shared.localization.StringId;

public final class StringIdParam extends TemplateBase<StringIdParam> {
    private StringIdParamData dataSingle;

    public StringId getValue() {
        switch (this.dataType) {
            case DataTypeId.Single:
                return new StringId(
                        this.dataSingle.table.getValue(),
                        this.dataSingle.index.getValue());
            case DataTypeId.WeightedList:
                logger.debug("Attempting to get value of weighted value list, but it is currently unimplemented.");
                break;
            default:
                logger.debug("Unknown data type <{}>.", this.dataType);
                break;
        }

        return new StringId("string_id_table", 0);
    }

    public void setValue(StringIdParamData value) {
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
    public void cleanSingleParam() {
        if (this.dataSingle != null) {
            this.dataSingle.table.cleanData();
            this.dataSingle.index.cleanData();
        }
    }

    @Override
    public StringIdParam createNewParam() { return new StringIdParam(); }

    @Override
    public StringIdParam createDeepCopy() {
        StringIdParam param = createNewParam();
        param.dataType = this.dataType;
        param.loaded = this.loaded;

        switch (this.dataType) {
            case DataTypeId.None:
                break;
            case DataTypeId.Single:
                StringIdParamData data = new StringIdParamData();
                data.table = this.dataSingle.table.createDeepCopy();
                data.index = this.dataSingle.index.createDeepCopy();
                param.dataSingle = data;
                break;
            case DataTypeId.WeightedList:
                WeightedValueList thisList = (WeightedValueList)this.data;
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
