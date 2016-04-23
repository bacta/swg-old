package com.ocdsoft.bacta.swg.shared.property;

import com.ocdsoft.bacta.swg.shared.object.GameObject;

public class LayerProperty extends Property {
    public static int getClassPropertyId() {
        return 0x78e8a2d2;
    }


    public LayerProperty(final GameObject owner) {
        super(getClassPropertyId(), owner);
    }
}
