package com.ocdsoft.bacta.swg.shared.util;

import javax.vecmath.Quat4f;

public class Quaternion {
	public static final double EPS = 0.000001;
	
	public static float convertToHeading(Quat4f q) {
		double mag = q.x * q.x + q.y * q.y + q.z * q.z;
		
		if (mag > EPS) {
			mag = Math.sqrt(mag);
			
			return (float) (2.0 * Math.atan2(mag, q.w)) / 6.283f * 100.f;
		} else {
			return 0;
		}
	}
}
