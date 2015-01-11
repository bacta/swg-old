package com.ocdsoft.bacta.swg.shared.object.template;

import com.ocdsoft.bacta.swg.shared.object.template.param.FloatParam;
import com.ocdsoft.bacta.swg.shared.object.template.param.IntegerParam;
import lombok.Getter;

/**
 * Created by crush on 3/4/14.
 */
public class SharedBattleFieldMarkerObjectTemplate extends SharedTangibleObjectTemplate {
    public SharedBattleFieldMarkerObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_SBMK; }

    @Getter
    private IntegerParam numberOfPoles = new IntegerParam();
    @Getter
    private FloatParam radius = new FloatParam();
}
