package de.bittner.colourkiste.math;

import de.bittner.colourkiste.math.geometry.Box;
import org.tinylog.Logger;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

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

    public static Vec2 mult(AffineTransform t, Vec2 worldPos) {
        final Point2D dest = new Point2D.Double();
        t.transform(worldPos.toPoint2D(), dest);
        return Vec2.from(dest);
    }

    public static Vec2 invert(AffineTransform t, Vec2 worldPos) {
        final Point2D dest = new Point2D.Double();
        try {
            t.inverseTransform(worldPos.toPoint2D(), dest);
        } catch (final NoninvertibleTransformException e) {
            Logger.error("Given non-invertible matrix");
        }
        return Vec2.from(dest);
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
