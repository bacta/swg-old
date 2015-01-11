package com.ocdsoft.bacta.swg.shared.iff.terrain.affector;

import com.ocdsoft.bacta.swg.shared.geometry.Rectangle;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3f;
import java.util.List;

/**
 * Created by crush on 8/13/2014.
 */
public class AffectorRibbon {
    public class Quad {
        Vector3f[] points;
    }

    int cachedTerrainShaderFamilyId;
    //ShaderGroup::Info cachedSgi;
    float waterShaderSize;
    float velocity;
    float capWidth;
    int terrainShaderFamilyId;
    //TerrainGeneratorFeatherFunction featherFunctionTerrainShader;
    float featherDistanceTerrainShader;
    byte ribbonWaterShaderTemplateName;
    //TerrainGeneratorWaterType waterType;
    //List<float> heightList;
    List<Vector2d> endCapPointList;
    Rectangle endCapExtent;
}
