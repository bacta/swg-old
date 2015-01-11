package com.ocdsoft.bacta.swg.shared.geometry;

public interface IShape {
	public float getArea();
	public float getPerimeter();
	public boolean containsPoint(float x, float y);
}
