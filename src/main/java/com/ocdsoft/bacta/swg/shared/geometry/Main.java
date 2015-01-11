package com.ocdsoft.bacta.swg.shared.geometry;

public class Main {
	public static void main(String[] args) {
		Circle shape = new Circle(2, 2, 1);
		
		System.out.println(shape.containsPoint(1, 0));
	}
}
