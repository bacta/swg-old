package com.ocdsoft.bacta.swg.shared.property;

import com.ocdsoft.bacta.swg.shared.object.GameObject;

public class CustomizationDataProperty extends Property {
    public static int getClassPropertyId() {
        return 0xDE36CB96;
    }

    public CustomizationDataProperty(final GameObject owner) {
        super(getClassPropertyId(), owner);
    }
}
