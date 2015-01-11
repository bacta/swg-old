package com.ocdsoft.bacta.swg.shared.iff.terrain.affector;

import com.ocdsoft.bacta.swg.shared.iff.terrain.Affector;

public class AffectorFloraStatic extends Affector {
    int cachedFamilyId;
    //FloraGroup::Info cachedFgi;
    float cachedDensity;
    int familyId;
    //TerrainGeneratorOperation operation;
    boolean removeAll;
    boolean densityOverride;
    float densityOverrideDensity;
}
