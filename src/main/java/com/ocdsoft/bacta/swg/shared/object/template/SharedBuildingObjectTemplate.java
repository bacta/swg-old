package com.ocdsoft.bacta.swg.shared.object.template;

import com.ocdsoft.bacta.swg.shared.object.template.param.StringParam;
import lombok.Getter;

/**
 * Created by crush on 3/4/14.
 */
public class SharedBuildingObjectTemplate extends SharedTangibleObjectTemplate {
    public SharedBuildingObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_SBOT; }

    @Getter
    private StringParam terrainModificationFileName = new StringParam();
    @Getter
    private StringParam interiorLayoutFileName = new StringParam();
}
