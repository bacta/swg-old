package com.ocdsoft.bacta.swg.shared.iff.terrain;

/**
 * Created by crush on 8/14/2014.
 */
public class TerrainGeneratorMap {
    public static final int TGM_height = 0x1;
    public static final int TGM_color = 0x2;
    public static final int TGM_shader = 0x4;
    public static final int TGM_floraStaticCollidable = 0x8;
    public static final int TGM_floraStaticNonCollidable = 0x10;
    public static final int TGM_floraDynamicNear = 0x20;
    public static final int TGM_floraDynamicFar = 0x40;
    public static final int TGM_environment = 0x80;
    public static final int TGM_vertexPosition = 0x100;
    public static final int TGM_vertexNormal = 0x200;
    public static final int TGM_exclude = 0x400;
    public static final int TGM_passable = 0x800;
    public static final int TGM_ALL = 0xFFFFFFFF;
}
