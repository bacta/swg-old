package com.ocdsoft.bacta.swg.shared.math;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import lombok.Data;
import org.magnos.steer.vec.Vec3;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 4/7/2016.
 */
@Data
public final class Vector implements ByteBufferWritable {
    public static final Vector UNIT_X = new Vector(1, 0, 0);
    public static final Vector UNIT_Y = new Vector(0, 1, 0);
    public static final Vector UNIT_Z = new Vector(0, 0, 1);
    public static final Vector NEGATIVE_UNIT_X = new Vector(-1, 0, 0);
    public static final Vector NEGATIVE_UNIT_Y = new Vector(0, -1, 0);
    public static final Vector NEGATIVE_UNIT_Z = new Vector(0, 0, -1);
    public static final Vector ZERO = new Vector(0, 0, 0);
    public static final Vector XYZ111 = new Vector(1, 1, 1);
    public static final float NORMALIZE_THRESHOLD = 0.00001f;
    public static final float NORMALIZE_EPSILON = 0.00001f;

    public float x;
    public float y;
    public float z;

    public Vector() {
        x = 0.f;
        y = 0.f;
        z = 0.f;
    }

    public Vector(final Vector vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public Vector(final Vec3 vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public Vector(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(final ByteBuffer buffer) {
        this.x = buffer.getFloat();
        this.y = buffer.getFloat();
        this.z = buffer.getFloat();
    }

    public final Vec3 asVec3() {
        return new Vec3(x, y, z);
    }

    public Vector add(final Vector rhs) {
        return new Vector(x + rhs.x, y + rhs.y, z + rhs.z);
    }

    public Vector subtract(final Vector rhs) {
        return new Vector(x - rhs.x, y - rhs.y, z - rhs.z);
    }

    public Vector multiply(final float scalar) {
        return new Vector(x * scalar, y * scalar, z * scalar);
    }

    public Vector divide(final float scalar) {
        return multiply(1 / scalar);
    }

    public Vector inverse() {
        return new Vector(-x, -y, -z);
    }

    /***
     * Normalizes the vector. If it succeeds, returns a new normalized Vector instance. Otherwise,
     * returns the original instance.
     *
     * @return
     */
    public Vector normalize() {
        final float mag = magnitude();

        if (mag < NORMALIZE_THRESHOLD)
            return this;

        return divide(mag);
    }

    /**
     * Calculate the square of the magnitude of this vector.
     * <p>
     * This routine is much faster than magnitude().
     *
     * @return The square of the magnitude of the vector
     * @see Vector::magnitude()
     */
    public float magnitudeSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * Calculate the approximate magnitude of this vector.
     * <p>
     * The implementation of this routine has +/- 8% error.
     *
     * @return The approximate magnitude of the vector
     */
    public float approximateMagnitude() {
        float minc = Math.abs(x);
        float midc = Math.abs(y);
        float maxc = Math.abs(z);

        // sort the vectors
        // we do our own swapping to avoid heavy-weight includes in such a low-level class
        if (midc < minc) {
            final float temp = midc;
            midc = minc;
            minc = temp;
        }

        if (maxc < minc) {
            final float temp = maxc;
            maxc = minc;
            minc = temp;
        }

        if (maxc < midc) {
            final float temp = maxc;
            maxc = midc;
            midc = temp;
        }

        return (maxc + (11.0f / 32.0f) * midc + (0.25f) * minc);
    }

    /**
     * Calculate the magnitude of this vector.
     * <p>
     * This routine is slow because it requires a square root operation.
     *
     * @return The magnitude of the vector
     * @see Vector::magnitudeSquared()
     */
    public float magnitude() {
        return (float) Math.sqrt(magnitudeSquared());
    }

    /**
     * Normalize a vector to a length of approximately 1.
     * <p>
     * If the vector is too small, it cannot be normalized.
     *
     * @return True if the vector has been approximately normalized, otherwise false.
     */
    public Vector approximateNormalize() {
        final float mag = approximateMagnitude();

        if (mag < NORMALIZE_THRESHOLD)
            return this;

        return divide(mag);
    }

    /**
     * Calculate the angle of the vector from the X-Z plane.
     * <p>
     * This routine uses sqrt() and atan2() so it is not particularly fast.
     *
     * @return The angle of the vector from the X-Z plane
     */
    public float phi() {
        return (float) Math.atan2(-y, Math.sqrt(x * x + z * z));
    }

    /**
     * Return the rotation of the vector around the Y plane.
     * <p>
     * The result is undefined if both the x and z values of the vector are zero.
     * <p>
     * This routine uses atan2() so it is not particularly fast.
     *
     * @return The rotation of the vector around the Y plane
     */
    public float theta() {
        return (float) Math.atan2(x, z);
    }

    /**
     * Compute the dot product between this vector and another vector.
     * <p>
     * The dot product value is equal to the cosine of the angle between
     * the two vectors multiplied by the sum of the lengths of the vectors.
     *
     * @param vector Vector to compute the dot product against
     */
    public float dot(final Vector vector) {
        return (x * vector.x) + (y * vector.y) + (z * vector.z);
    }

    /**
     * Calculate the cross product between two vectors.
     * <p>
     * This routine returns a temporary.
     * <p>
     * Cross products are not communitive.
     *
     * @param rhs The right-hand size of the expression
     * @return A vector that is the result of the cross product of the two vectors.
     */
    public Vector cross(final Vector rhs) {
        return new Vector(y * rhs.z - z * rhs.y, z * rhs.x - x * rhs.z, x * rhs.y - y * rhs.x);
    }

    /**
     * Compute the midpoint of two vectors.
     * <p>
     * This routine just averages the three components separately.
     *
     * @param vector1 First endpoint
     * @param vector2 Second endpoint
     */
    public static Vector midpoint(final Vector vector1, final Vector vector2) {
        return new Vector(
                (float) ((vector1.x + vector2.x) * 0.5),
                (float) ((vector1.y + vector2.y) * 0.5),
                (float) ((vector1.z + vector2.z) * 0.5));
    }

    public static Vector perpendicular(final Vector direction) {
        // Measure the projection of "direction" onto each of the axes
        final float id = Math.abs(direction.dot(UNIT_X));
        final float jd = Math.abs(direction.dot(UNIT_Y));
        final float kd = Math.abs(direction.dot(UNIT_Z));

        Vector result;

        if (id <= jd && id <= kd)
            // Projection onto i was the smallest
            result = direction.cross(UNIT_X);
        else if (jd <= id && jd <= kd)
            // Projection onto j was the smallest
            result = direction.cross(UNIT_Y);
        else
            // Projection onto k was the smallest
            result = direction.cross(UNIT_Z);

        result.normalize();

        return result;
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putFloat(x);
        buffer.putFloat(y);
        buffer.putFloat(z);
    }
}
