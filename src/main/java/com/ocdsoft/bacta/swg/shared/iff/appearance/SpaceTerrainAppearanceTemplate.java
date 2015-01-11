package com.ocdsoft.bacta.swg.shared.iff.appearance;

public class SpaceTerrainAppearanceTemplate extends AppearanceTemplate {

    public class LightData {
        //bool dot3
        //VectorArgb diffuseColor
        //VectorArgb specularColor
        //Vector3 direction
    }

    public class CelestialData {
        //PersistentCrcString backSharedTemplateName
        //float backSize
        //PersistentCrcString fontSharedTemplateName
        //float frontSize
        //Vector3 direction
    }

    public class DistanceAppearanceData {
        //PersistentCrcString appearanceTemplateName
        //Vector3 direction
        //Vector3 orientation
        //float haloRoll
        //float haloScale
        //bool infiniteDistance
    }

    //PackedRgb clearColor
    //VectorArgb ambientColor
    //List<LightData> lightDataList
    //PersistentCrcString environmentTextureName
    //bool fogEnabled
    //PackedArgb fogColor
    //float fogDensity
    //int numberOfStars
    //PersistentCrcString starColorRampName
    //int numberOfDust
    //float dustRadius
    //bool skyBoxCubeMap
    //PersistentCrcString skyBoxTextureNameMask
    //List<CelestialData> celestialDataList
    //List<DistanceAppearanceData> distanceAppearanceList
    //float mapWidthInMeters
}
