package com.ocdsoft.bacta.swg.shared.customization;

import lombok.Getter;

/**
 * Created by crush on 3/31/14.
 */
public abstract class RangedIntCustomizationVariable implements CustomizationVariable {
    @Getter
    private final int min;

    @Getter
    private final int max;

    public RangedIntCustomizationVariable(int min, int max) {
        this.min = min;
        this.max = max;
    }
}
