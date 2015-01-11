package com.ocdsoft.bacta.swg.shared.iff.terrain.affector;

import com.ocdsoft.bacta.swg.shared.iff.terrain.Affector;

public class AffectorShaderConstant extends Affector {
    int cachedFamilyId;
    //SharedGroup::Info cachedSgi;
    float cachedFeatherClamp;
    int familyId;
    boolean useFeatherClampOverride;
    float featherClampOverride;
}
