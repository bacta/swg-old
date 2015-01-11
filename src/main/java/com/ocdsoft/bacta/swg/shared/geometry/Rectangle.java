package com.ocdsoft.bacta.swg.shared.geometry;

public class Rectangle implements IShape {
	protected float length;
	protected float width;
	
	public Rectangle(float length, float width) {
		this.length = length;
		this.width = width;
	}
	
	@Override
	public float getArea() {
		return length * width;
	}

	@Override
	public float getPerimeter() {
		return 2 * (length + width);
	}

	@Override
	public boolean containsPoint(float x, float y) {
		return (length <= y && width <= x);
	}
}
