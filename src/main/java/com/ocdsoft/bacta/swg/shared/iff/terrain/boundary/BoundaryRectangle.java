package com.ocdsoft.bacta.swg.shared.iff.terrain.boundary;

import com.ocdsoft.bacta.swg.shared.geometry.Rectangle;
import com.ocdsoft.bacta.swg.shared.iff.terrain.Boundary;

public class BoundaryRectangle extends Boundary {
    Rectangle rectangle;
    Rectangle innerRectangle;
    boolean useTransform;
    //Transform2d transform;
    boolean localWaterTable;
    boolean localGlobalWaterTable;
    float localWaterTableHeight;
    float localWaterTableShaderSize;
    String localWaterTableShaderTemplateName;
    //TerrainGeneratorWaterType waterType;
}
