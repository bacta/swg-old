package com.ocdsoft.bacta.swg.shared.math;

/**
 * Created by crush on 4/23/2016.
 */
public class Rectangle2d {
    public float x0;
    public float y0;
    public float x1;
    public float y1;

    public void set(float newX0, float newY0, float newX1, float newY1) {

    }

    float getWidth() {
        return 0.0f;
    }

    public float getHeight() {
        return 0.0f;
    }

    public Vector2d getCenter() {
        return new Vector2d();
    }

    public boolean isWithin(float x, float y) {
        return false;
    }

    public boolean isWithin(final Vector2d point) {
        return false;
    }

    public boolean isVector2d() {
        return false;
    }

    public void expand(float x, float y) {

    }

    public void expand(final Vector2d point) {

    }

    public void expand(final Rectangle2d rectangle) {

    }

    public void translate(float x, float y) {

    }

    public void translate(final Vector2d point) {

    }

    public void scale(float scalar) {

    }

    public boolean intersects(final Rectangle2d other) {
        return false;
    }

    public boolean contains(final Rectangle2d other) {
        return false;
    }

    public boolean intersects(final Line2d line) {
        return false;
    }
}
