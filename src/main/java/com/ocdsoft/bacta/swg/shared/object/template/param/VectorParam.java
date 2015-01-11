package com.ocdsoft.bacta.swg.shared.object.template.param;

/**
 * Created by crush on 3/4/14.
 */
public final class VectorParam extends TemplateBase<VectorParam> {
    private VectorParamData dataSingle;

    public VectorParamData getSingle() {
        return dataSingle;
    }

    @Override
    public VectorParam createNewParam() { return new VectorParam(); }

    @Override
    public VectorParam createDeepCopy() {
        VectorParam param = createNewParam();
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
