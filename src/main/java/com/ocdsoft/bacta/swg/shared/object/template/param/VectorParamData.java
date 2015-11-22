package com.ocdsoft.bacta.swg.shared.object.template.param;

import javax.vecmath.Vector3f;
import java.util.Random;

/**
 * Created by crush on 11/21/2015.
 */
public final class VectorParamData {
    private static final Random random = new Random();
    private static final float PI_TIMES_2 = 3.14159265358979323846f * 2.0f;

    public boolean ignoreY;
    public FloatParam x;
    public FloatParam y;
    public FloatParam z;
    public FloatParam radius;

    /**
     * Returns a new vector transformed based on the info in this param.
     *
     * @param inputPosition The vector to transform.
     * @return A new vector transformed.
     */
    Vector3f adjustVector(final Vector3f inputPosition) {
        final Vector3f transformedPosition = new Vector3f();

        transformedPosition.x = inputPosition.x + x.getValue();

        if (!ignoreY)
            transformedPosition.y = inputPosition.y + y.getValue();

        transformedPosition.z = inputPosition.z + z.getValue();

        float rad = radius.getValue();

        if (rad > 0.0f) {
            if (ignoreY) {
                float phi = random.nextFloat() * PI_TIMES_2;
                transformedPosition.x += rad * Math.cos(phi);
                transformedPosition.z += rad * Math.sin(phi);
            } else {
                // uniform random point on a sphere from
                // http://www.cs.cmu.edu/~mws/rpos.html
                float y = random.nextFloat() * rad * 2 - rad; //Random::randomReal(-rad, rad)
                float phi = random.nextFloat() * PI_TIMES_2;
                float ctheta = (float) Math.cos(Math.asin(y / rad)) * rad;
                transformedPosition.x += ctheta * Math.cos(phi);
                transformedPosition.z += ctheta * Math.sin(phi);
                transformedPosition.y += y;
            }
        }

        return transformedPosition;
    }
}
