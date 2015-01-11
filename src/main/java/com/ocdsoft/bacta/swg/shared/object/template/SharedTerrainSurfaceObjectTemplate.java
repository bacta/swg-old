package com.ocdsoft.bacta.swg.shared.object.template;

import com.ocdsoft.bacta.swg.shared.object.template.param.FloatParam;
import com.ocdsoft.bacta.swg.shared.object.template.param.StringParam;
import lombok.Getter;

/**
 * Created by crush on 4/3/14.
 */
public class SharedTerrainSurfaceObjectTemplate extends ObjectTemplate {
    public SharedTerrainSurfaceObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_STER; } //TODO: Check this type id.

    @Getter
    private final FloatParam cover = new FloatParam();
    @Getter
    private final StringParam surfaceType = new StringParam();
}
