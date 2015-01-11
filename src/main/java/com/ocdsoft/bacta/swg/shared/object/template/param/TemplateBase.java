package com.ocdsoft.bacta.swg.shared.object.template.param;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by crush on 3/4/14.
 */
public abstract class TemplateBase<T extends TemplateBase> {
    protected static final Random random = new Random();

    protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Getter protected int dataType;
    @Getter protected boolean loaded;
    protected DataType data;

    public abstract T createNewParam();
    public abstract T createDeepCopy();

    public void cleanSingleParam() {}

    public void cleanData() {
        cleanSingleParam();
        this.data = null;
        this.dataType = DataTypeId.None;
        this.loaded = false;
    }

    protected interface DataType {}

    protected static final class DataTypeId {
        protected static final int None = 0x00;
        protected static final int Single = 0x01;
        protected static final int WeightedList = 0x02;
        protected static final int Range = 0x03;
        protected static final int DieRoll = 0x04;
    }

    protected static final class WeightedValue {
        protected WeightedValue() {}

        protected WeightedValue(WeightedValue that) {
            this.weight = that.weight;
            this.value = that.value.createDeepCopy();
        }

        protected TemplateBase value;
        protected int weight;
    }

    protected static final class WeightedValueList extends ArrayList<WeightedValue> implements DataType {
        protected WeightedValueList() {
            super();
        }

        protected WeightedValueList(int capacity) {
            super(capacity);
        }
    }
}
