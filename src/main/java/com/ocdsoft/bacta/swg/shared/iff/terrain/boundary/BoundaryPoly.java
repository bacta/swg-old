package com.ocdsoft.bacta.swg.shared.iff.terrain.boundary;

import com.ocdsoft.bacta.swg.shared.geometry.Rectangle;
import com.ocdsoft.bacta.swg.shared.iff.terrain.Boundary;

import javax.vecmath.Vector2d;
import java.util.List;

public class BoundaryPoly extends Boundary {
    List<Vector2d> pointList;
    Rectangle extent;
}
