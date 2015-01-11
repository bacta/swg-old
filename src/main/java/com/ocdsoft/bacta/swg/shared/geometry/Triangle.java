package com.ocdsoft.bacta.swg.shared.geometry;

public class Triangle implements IShape {
	protected float base;
	protected float height;
	
	public Triangle(float base, float height) {
		this.base = base;
		this.height = height;
	}

	@Override
	public float getArea() {
		return 0.5f * base * height;
	}

	@Override
	public float getPerimeter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean containsPoint(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

}
