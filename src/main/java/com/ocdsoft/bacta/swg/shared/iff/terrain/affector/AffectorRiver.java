package com.ocdsoft.bacta.swg.shared.iff.terrain.affector;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3f;

public class AffectorRiver extends AffectorBoundaryPoly {
    public class WaterTable {
        Vector3f points[]; //4
        Vector2d direction;
    }

    int cachedBankFamilyId;
    //ShaderGroup::Info cachedBankSgi;
    int cachedBottomFamilyId;
    //SharedGroup::Info cachedBottomSgi;
    //MultiFractal multiFractal;
    int bankFamilyId;
    int bottomFamilyId;
    float trenchDepth;
    boolean hasLocalWaterTable;
    float localWaterTableDepth;
    float localWaterTableWidth;
    float localWaterTableShaderSize;
    String localWaterTableShaderTemplateName;
    float velocity;
    //TerrainGeneratorWaterType waterType;
}
