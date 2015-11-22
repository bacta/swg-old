package com.ocdsoft.bacta.swg.shared.object.template.param;

import lombok.Getter;

/**
 * Created by crush on 11/21/2015.
 */
public final class TriggerVolumeData {
    @Getter
    private final String name;
    @Getter
    private float radius;

    public TriggerVolumeData() {
        name = null;
        radius = 0.0f;
    }

    public TriggerVolumeData(final String name, float radius) {
        this.name = name;
        this.radius = radius;
    }
}
