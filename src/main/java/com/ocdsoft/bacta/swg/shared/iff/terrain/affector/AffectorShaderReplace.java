package com.ocdsoft.bacta.swg.shared.iff.terrain.affector;

import com.ocdsoft.bacta.swg.shared.iff.terrain.Affector;

public class AffectorShaderReplace extends Affector {
    int cachedFamilyId;
    //ShaderGroup::Info cachedSgi;
    float cachedFeatherClamp;
    int sourceFamilyId;
    int destinationFamilyId;
    boolean useFeatherClampOverride;
    float featherClampOverride;
}
