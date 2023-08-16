package de.bittner.colourkiste.engine.components.hitbox;

import de.bittner.colourkiste.math.geometry.Box;

public class StaticRectangleHitbox extends RectangleHitbox {
    private Box box;

    public StaticRectangleHitbox(Box box) {
        this.box = box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    @Override
    public Box getBox() {
        return box;
    }
}
