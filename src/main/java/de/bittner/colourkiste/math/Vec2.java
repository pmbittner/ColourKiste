package de.bittner.colourkiste.math;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public record Vec2(double x, double y) {
    public static Vec2 all(double val) {
        return new Vec2(val, val);
    }

    public static Vec2 from(final Point2D p) {
        return new Vec2(p.getX(), p.getY());
    }

    public static Vec2 from(Dimension d) {
        return new Vec2(
                d.width,
                d.height
        );
    }

    public Point2D.Double toPoint2D() {
        return new Point2D.Double(x(), y());
    }

    public Vec2 withX(double newX) {
        return new Vec2(
                newX,
                y()
        );
    }

    public Vec2 withXMin(double xMin) {
        return new Vec2(
                Math.max(xMin, x()),
                y()
        );
    }

    public Vec2 withXMax(double xMax) {
        return new Vec2(
                Math.min(xMax, x()),
                y()
        );
    }

    public Vec2 withY(double newY) {
        return new Vec2(
                x(),
                newY
        );
    }

    public Vec2 withYMin(double yMin) {
        return new Vec2(
                x(),
                Math.max(yMin, y())
        );
    }

    public Vec2 withYMax(double yMax) {
        return new Vec2(
                x(),
                Math.min(yMax, y())
        );
    }

    public Vec2 scale(double s) {
        return new Vec2(
                s * x(),
                s * y()
        );
    }

    public Vec2 scale(Vec2 s) {
        return new Vec2(
                s.x() * x(),
                s.y() * y()
        );
    }

    public Vec2 dividedBy(Vec2 divisor) {
        return new Vec2(
                this.x() / divisor.x(),
                this.y() / divisor.y()
        );
    }

    public Vec2 add(Vec2 b) {
        return add(b.x(), b.y());
    }

    public Vec2 add(double x, double y) {
        return new Vec2(
                x() + x,
                y() + y
        );
    }

    public Vec2 flip() {
        return new Vec2(
                -x(),
                -y()
        );
    }

    public Vec2 minus(Vec2 b) {
        return this.add(b.flip());
    }

    public Vec2 minusX(Vec2 b) {
        return new Vec2(
                x() - b.x(),
                y()
        );
    }

    public Vec2 minusY(Vec2 b) {
        return new Vec2(
                x(),
                y() - b.y()
        );
    }

    public Vec2 normalize() {
        final double l = length();
        return new Vec2(
                x() / l,
                y() / l
        );
    }

    public double length() {
        return Math.sqrt(x() * x() + y() * y());
    }

    public double distanceTo(Vec2 b) {
        return this.minus(b).length();
    }

    public Vec2 roundToInt() {
        return new Vec2(
                Math.round(x()),
                Math.round(y())
        );
    }

    public Vec2 castToInt() {
        return new Vec2(
                (int) x(),
                (int) y()
        );
    }

    public Vec2 snapToPixels(int width, int height) {
        final double evenWidth  = 0.5 * (width  % 2);
        final double evenHeight = 0.5 * (height % 2);
        return new Vec2(
                Math.round(evenWidth  + x()) - evenWidth,
                Math.round(evenHeight + y()) - evenHeight
        );
    }

    public Vec2 rotate90DegreesClockwise() {
        return new Vec2(
                y(),
                -x()
        );
    }

    public Vec2 rotate90DegreesCounterClockwise() {
        return new Vec2(
                -y(),
                x()
        );
    }

    public Vec2 transform(final AffineTransform t) {
        final Point2D.Double dest = new Point2D.Double();
        t.transform(this.toPoint2D(), dest);
        return from(dest);
    }

    public Vec2 deltaTransform(final AffineTransform t) {
        final Point2D.Double dest = new Point2D.Double();
        t.deltaTransform(this.toPoint2D(), dest);
        return from(dest);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
