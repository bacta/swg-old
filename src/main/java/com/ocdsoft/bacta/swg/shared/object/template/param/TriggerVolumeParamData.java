package com.ocdsoft.bacta.swg.shared.object.template.param;

/**
 * Created by crush on 11/21/2015.
 */
public final class TriggerVolumeParamData {
    public StringParam name;
    public FloatParam radius;

    public TriggerVolumeParamData() {
        this.name = new StringParam();
        this.radius = new FloatParam();
    }
}
