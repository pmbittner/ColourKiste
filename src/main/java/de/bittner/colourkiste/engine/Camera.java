package de.bittner.colourkiste.engine;

import de.bittner.colourkiste.math.Vec2;

public class Camera
{
    private static final double zoomMin = 0.5, zoomMax = 50;

    private Vec2 location;
    private double zoom;

    public Camera() {
        reset();
    }

    /**
     * The camera zooms the given steps. If steps > 0 the camera will zoom in, else out.
     * x and y are the point, the camera should zoom at.
     */
    public void zoomTowards(int steps, Vec2 pos) {
        double zoomPrev = zoom;
        double newZoomSqrt = 0.2*steps + Math.sqrt(zoom);
        zoom = Math.min(zoomMax, Math.max(zoomMin, newZoomSqrt * newZoomSqrt));

        double dZoom = zoom - zoomPrev;
        this.location = this.location.minus(pos.scale(dZoom));
    }

    public void setZoom(double zoom) {
        if (zoom == 0)
            throw new IllegalArgumentException("Zoom can't be zero!");
        this.zoom = zoom;
    }

    public double getZoom() { return zoom; }

    public void setLocation(final Vec2 location) {
        this.location = location;
    }

    public Vec2 getLocation() {
        return location;
    }

    public void reset() {
        location = new Vec2(0, 0);
        zoom = 1;
    }
}
