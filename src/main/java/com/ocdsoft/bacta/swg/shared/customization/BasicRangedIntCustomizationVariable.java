package com.ocdsoft.bacta.swg.shared.customization;

import lombok.Getter;

/**
 * Created by crush on 3/31/14.
 */
public final class BasicRangedIntCustomizationVariable extends RangedIntCustomizationVariable {
    //int value
    //int minRangeInclusive
    //int maxRangeExclusive


    @Getter
    private final int defaultValue;

    public BasicRangedIntCustomizationVariable(int min, int max, int defaultValue) {
        super(min, max);

        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return String.format("{ min: %d; max: %d; def: %d }",
                getMin(),
                getMax(),
                getDefaultValue());
    }
}
