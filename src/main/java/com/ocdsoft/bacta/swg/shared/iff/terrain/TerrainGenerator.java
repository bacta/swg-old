package com.ocdsoft.bacta.swg.shared.iff.terrain;

/**
 * Created by crush on 8/13/2014.
 */
public class TerrainGenerator {
    //TerrainGeneratorBoundaryType
    private static final int TGBT_circle = 0x0;
    private static final int TGBT_rectangle = 0x1;
    private static final int TGBT_polygon = 0x2;
    private static final int TGBT_polyline = 0x3;

    //TerrainGeneratorFeatherFunction
    private static final int TGFF_linear = 0x0;
    private static final int TGFF_easeIn = 0x1;
    private static final int TGFF_easeOut = 0x2;
    private static final int TGFF_easeInOut = 0x3;
    private static final int TGFF_COUNT = 0x4;

    //TerrainGeneratorFilterType
    private static final int TGFT_height = 0x0;
    private static final int TGFT_fractal = 0x1;
    private static final int TGFT_slope = 0x2;
    private static final int TGFT_direction = 0x3;
    private static final int TGFT_shader = 0x4;
    private static final int TGFT_bitmap = 0x5;

    //TerrainGeneratorAffectorType
    private static final int TGAT_heightTerrace = 0x0;
    private static final int TGAT_heightConstant = 0x1;
    private static final int TGAT_heightFractal = 0x2;
    private static final int TGAT_colorConstant = 0x3;
    private static final int TGAT_colorRampHeight = 0x4;
    private static final int TGAT_colorRampFractal = 0x5;
    private static final int TGAT_shaderConstant = 0x6;
    private static final int TGAT_shaderReplace = 0x7;
    private static final int TGAT_floraStaticCollidableConstant = 0x8;
    private static final int TGAT_floraStaticNonCollidableConstant = 0x9;
    private static final int TGAT_floraDynamicNearConstant = 0xA;
    private static final int TGAT_floraDynamicFarConstant = 0xB;
    private static final int TGAT_exclude = 0xC;
    private static final int TGAT_passable = 0xD;
    private static final int TGAT_road = 0xE;
    private static final int TGAT_river = 0xF;
    private static final int TGAT_environment = 0x10;
    private static final int TGAT_ribbon = 0x11;
    private static final int TGAT_COUNT = 0x12;
}
