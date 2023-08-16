package de.bittner.colourkiste.engine.components.hitbox;

import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.math.geometry.Box;

public abstract class RectangleHitbox extends Hitbox {
    public abstract Box getBox();

    @Override
    public boolean contains(Vec2 worldPos) {
        return getBox().contains(getEntity().toEntitySpace(worldPos));
    }
}
