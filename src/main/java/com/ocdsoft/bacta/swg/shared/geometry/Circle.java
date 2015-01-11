package com.ocdsoft.bacta.swg.shared.geometry;

public class Circle implements IShape {
	protected float a;
	protected float b;
	protected float r;
	
	public Circle(float r) {
		//this.a = 0;
		//this.b = 0;
		this.r = r;
	}
	
	public Circle(float a, float b, float r) {
		this.a = a;
		this.b = b;
		this.r = r;
	}

	@Override
	public float getArea() {
		return (float) (Math.PI * r * r);
	}

	@Override
	public float getPerimeter() {
		return (float) (2 * Math.PI * r);
	}

	@Override
	public boolean containsPoint(float x, float y) {
		return ((a - x) * (a - x)) + ((b - y) * (b - y)) == r * r;
	}
}
