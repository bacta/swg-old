package com.ocdsoft.bacta.swg.shared.iff.terrain.affector;

import com.ocdsoft.bacta.swg.shared.iff.terrain.Affector;

public class AffectorFloraDynamic extends Affector {
    int cachedFamilyId;
    //RadialGroup::Info cachedRgi;
    float cachedDensity;
    int familyId;
    //TerrainGeneratorOperation operation;
    boolean removeAll;
    boolean densityOverride;
    float densityOverrideDensity;
}
