package com.ocdsoft.bacta.swg.shared.iff.appearance;

public class ProceduralTerrainAppearanceTemplate extends AppearanceTemplate {
    String name;
    float mapWidthInMeters;
    float chunkWidthInMeters;
    int numberOfTilesPerChunk;
    boolean useGlobalWaterTable;
    float globalWaterTableHeight;
    float globalWaterTableShaderSize;
    String globalWaterTableShaderTemplateName;
    float environmentCycleTime;
    float collidableMinimumDistance;
    float collidableMaximumDistance;
    float collidableTileSize;
    float collidableTileBorder;
    int collidableSeed;
    float nonCollidableMinimumDistance;
    float nonCollidableMaximumDistance;
    float nonCollidableTileSize;
    float nonCollidableTileBorder;
    int nonCollidableSeed;
    float radialMinimumDistance;
    float radialMaximumDistance;
    float radialTileSize;
    float radialTileBorder;
    int radialSeed;
    float farRadialMinimumDistance;
    float farRadialMaximumDistance;
    float farRadialTileSize;
    float farRadialTileBorder;
    int farRadialSeed;
    boolean legacyMap; //TODO: Does this actually exist?
    //TerrainGenerator terrainGenerator;
    //Bakedterrain bakedTerrain;
    //PackedIntegerMap staticCollidableFloraMap;
    //PackedFixedPointMap staticCollidableFloraHeightMap;
}
