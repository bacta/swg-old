package com.ocdsoft.bacta.swg.shared.customization;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 3/31/14.
 */
public final class PaletteColorCustomizationVariable extends RangedIntCustomizationVariable {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Getter
    private final String paletteName;

    @Getter
    private final int defaultValue;

    public PaletteColorCustomizationVariable(final String paletteName, final int defaultValue) {
        super(0, 0);

        this.paletteName = paletteName;
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return String.format("{ paletteName: %s; defaultValue: %d }",
                getPaletteName(),
                getDefaultValue());
    }
}
