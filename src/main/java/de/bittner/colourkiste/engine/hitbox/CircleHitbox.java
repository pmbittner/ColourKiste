package de.bittner.colourkiste.engine.hitbox;

import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.math.geometry.Circle;

public class CircleHitbox extends Hitbox {
    private Circle circle;

    public CircleHitbox(final Circle circle) {
        this.circle = circle;
    }

    public void setRadius(Circle circle) {
        this.circle = circle;
    }

    public Circle getCircle() {
        return circle;
    }

    @Override
    public boolean contains(final Vec2 point) {
        return getEntity().getLocation().distanceTo(point) <= circle.radius();
    }
}
