package de.bittner.colourkiste.engine.components.hitbox;

import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.math.geometry.Box;

public class RectangleHitbox extends Hitbox {
    private Box box;

    public RectangleHitbox(Box box) {
        this.box = box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Box getBox() {
        return box;
    }

    @Override
    public boolean contains(Vec2 worldPos) {
        return box.contains(getEntity().toEntitySpace(worldPos));
    }
}
