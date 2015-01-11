package com.ocdsoft.bacta.swg.shared.object.template.param;

import com.ocdsoft.bacta.swg.shared.object.template.ObjectTemplate;

/**
 * Created by crush on 3/4/14.
 */
public class StructParam<T extends ObjectTemplate> extends TemplateBase<StructParam<T>> {
    private T dataSingle;

    public T getValue() {
        T result = null;

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

    public T getSingle() {
        return dataSingle;
    }

    public T getRange() { throw new UnsupportedOperationException(); }

    public T getDieRoll() { throw new UnsupportedOperationException(); }

    public void setValue(T value) {
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
        this.dataSingle = null;
    }

    @Override
    public StructParam<T> createNewParam() { return new StructParam<T>(); }

    @Override
    public StructParam<T> createDeepCopy() {
        StructParam<T> param = createNewParam();
        param.dataType = this.dataType;
        param.loaded = this.loaded;

        switch (this.dataType) {
            case DataTypeId.None:
                break;
            case DataTypeId.Single:
                //We want to copy the reference here because it is a reference to a shared object template
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
