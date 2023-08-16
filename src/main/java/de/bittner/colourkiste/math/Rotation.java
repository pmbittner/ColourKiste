package de.bittner.colourkiste.math;

import java.awt.geom.AffineTransform;

public class Rotation {
    public static double toRadians(double degrees) {
        return degrees * Math.PI / 180.0;
    }

    public static double toDegrees(double radians) {
        return radians * 180 / Math.PI;
    }
}
