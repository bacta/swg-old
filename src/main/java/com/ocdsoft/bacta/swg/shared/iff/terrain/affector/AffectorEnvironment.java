package com.ocdsoft.bacta.swg.shared.iff.terrain.affector;

import com.ocdsoft.bacta.swg.shared.iff.terrain.Affector;

public class AffectorEnvironment extends Affector {
    int cachedFamilyId;
    //EnvironmentGroup::Info cachedEgi;
    float cachedFeatherClamp;
    int familyId;
    boolean useFeatherClampOverride;
    float featherClampOverride;
}
