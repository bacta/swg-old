package com.ocdsoft.bacta.swg.shared.util;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Quat4f;

public class TestClass {

	public static double staticMethod1(Quat4f q) {
        double angle;

        float dirW = q.w;

        if (q.w * q.w + q.y * q.y > 0.0f) {
            if (q.w > 0.f && q.y < 0.0f)
                dirW *= -1.0f;

            angle = 2.0f * Math.acos(dirW);
        } else {
            angle = 0.0f;
        }

        return angle / 6.283f * 100.f;
    }
	
	public static AxisAngle4f axisAngle = new AxisAngle4f();

    public static double staticMethod2(Quat4f q) {
        axisAngle.set(q);
        return axisAngle.angle / 6.283f * 100.f;
    }
    
    public static abstract class Test {
    	private final String name;
    	
    	public Test(String name) {
    		this.name = name;
    	}
    	
    	public abstract void test();
    	
    	public String getName() { return name; }
    }

    public static final void main(String[] args) {
        final Quat4f quaternion = new Quat4f(0, 0, 0, 1);

        Test[] tests = new Test[] {
            new Test("staticMethod1") {
                @Override
                public void test() {
                    staticMethod1(quaternion);
                }
            },
            new Test("staticMethod2") {
                @Override
                public void test() {
                    staticMethod2(quaternion);
                }
            }
        };

        long startTime = 0;
        int repeat = 10; //How many times to repeat each iteration.
        int[] tiers = new int[] { 1000, 10000, 100000, 1000000 };
        long[][][] times = new long[tests.length][tiers.length][repeat];

        for (int testIndex = 0; testIndex < tests.length; testIndex++) {
            for (int tierIndex = 0; tierIndex < tiers.length; tierIndex++) {
                for (int r = 0; r < repeat; r++) {
                    startTime = System.nanoTime();

                    for (int i = 0; i < tiers[tierIndex]; i++) {
                        tests[testIndex].test(); //run the test
                    }

                    times[testIndex][tierIndex][r] = System.nanoTime() - startTime; //Stash the execution time in the array.
                }
            }
        }
        
        for (int i = 0; i < tiers.length; i++) {
        	System.out.print(String.format("%d\t", tiers[i]));
        }
        
        for (int ti = 0; ti < tests.length; ti++) {
        	System.out.println();
        	System.out.println(tests[ti].getName());
        	
        	for (int k = 0; k < repeat; k++) {
        		for (int t = 0; t < tiers.length; t++) {
        			System.out.print(String.format("%d\t", times[ti][t][k]));
        		}
        		System.out.println();
        	}
        }
    }
}
