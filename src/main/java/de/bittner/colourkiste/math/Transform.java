package de.bittner.colourkiste.math;

import de.bittner.colourkiste.math.geometry.Box;

import java.awt.geom.AffineTransform;

public final class Transform {
    public static AffineTransform mult(AffineTransform l, AffineTransform r) {
        final AffineTransform result = new AffineTransform(l);
        result.concatenate(r);
        return result;
    }

    public static AffineTransform mult(AffineTransform... rs) {
        AffineTransform result = new AffineTransform();
        for (int i = rs.length - 1; i >= 0; --i) {
            result = mult(rs[i], result);
        }
        return result;
    }

    public static void setAsBox(final AffineTransform transform, double width, double height) {
        transform.setTransform(
                1, 0,
                0, 1,
                -width / 2.0,
                -height / 2.0);
    }

    public static AffineTransform box(double width, double height) {
        final AffineTransform transform = new AffineTransform();
        transform.setTransform(
                1, 0,
                0, 1,
                -width / 2.0,
                -height / 2.0);
        return transform;
    }

    public static Box box(AffineTransform t, double width, double height)
    {
        return new Box(
                new Vec2(-width / 2.0, -height / 2.0).transform(t),
                new Vec2( width / 2.0,  height / 2.0).transform(t)
        );
    }
}
