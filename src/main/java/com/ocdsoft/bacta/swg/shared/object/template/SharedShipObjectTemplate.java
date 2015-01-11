package com.ocdsoft.bacta.swg.shared.object.template;

import com.ocdsoft.bacta.swg.shared.object.template.param.BoolParam;
import com.ocdsoft.bacta.swg.shared.object.template.param.StringParam;
import lombok.Getter;

/**
 * Created by crush on 3/4/14.
 */
public class SharedShipObjectTemplate extends SharedTangibleObjectTemplate {
    public SharedShipObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_SSHP; }

    @Getter
    private StringParam interiorLayoutFileName = new StringParam();
    @Getter
    private BoolParam hasWings = new BoolParam();
    @Getter
    private BoolParam playerControlled = new BoolParam();
    @Getter
    private StringParam cockpitFilename = new StringParam();
}
