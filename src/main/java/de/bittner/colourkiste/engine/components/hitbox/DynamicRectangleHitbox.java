package de.bittner.colourkiste.engine.components.hitbox;

import de.bittner.colourkiste.math.geometry.Box;

import java.util.function.Supplier;

public class DynamicRectangleHitbox extends RectangleHitbox {
    private final Supplier<Box> getBox;

    public DynamicRectangleHitbox(final Supplier<Box> getBox) {
        this.getBox = getBox;
    }

    @Override
    public Box getBox() {
        return getBox.get();
    }
}
