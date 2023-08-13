package de.bittner.colourkiste.math.geometry;

import de.bittner.colourkiste.math.Vec2;

public record Box(Vec2 upperLeft, Vec2 lowerRight) {
    public Box(Vec2 upperLeft, double width, double height) {
        this(upperLeft, upperLeft.add(width, height));
    }

    public double getWidth() {
        return lowerRight.x() - upperLeft().x();
    }

    public double getHeight() {
        return lowerRight.y() - upperLeft.y();
    }

    public Box shrink(Vec2 delta) {
        return new Box(
                this.upperLeft().add(delta),
                this.lowerRight.minus(delta)
        );
    }

    public Box shrink(double delta) {
        return shrink(new Vec2(delta, delta));
    }

    public boolean contains(Vec2 point) {
        return
                upperLeft.x() <= point.x()
                && upperLeft.y() <= point.y()
                && lowerRight.x() >= point.x()
                && lowerRight.y() >= point.y();
    }

    @Override
    public String toString() {
        return "Box{" +
                "upperLeft=" + upperLeft +
                ", lowerRight=" + lowerRight +
                '}';
    }
}
