package com.ocdsoft.bacta.swg.shared.object.template;

import com.ocdsoft.bacta.swg.shared.object.template.param.FloatParam;
import lombok.Getter;

/**
 * Created by crush on 8/13/2014.
 */
public class SharedVehicleObjectTemplate extends SharedTangibleObjectTemplate {
    public SharedVehicleObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_SVOT; }

    @Getter private final FloatParam[] speed = new FloatParam[] {
            new FloatParam(),
            new FloatParam(),
            new FloatParam(),
            new FloatParam(),
            new FloatParam()
    };
    @Getter private final FloatParam slopeAversion = new FloatParam();
    @Getter private final FloatParam hoverValue = new FloatParam();
    @Getter private final FloatParam turnRate = new FloatParam();
    @Getter private final FloatParam maxVelocity = new FloatParam();
    @Getter private final FloatParam acceleration = new FloatParam();
    @Getter private final FloatParam braking = new FloatParam();
}
