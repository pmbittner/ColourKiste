package de.bittner.colourkiste.engine.hitbox;

import de.bittner.colourkiste.engine.EntityComponent;
import de.bittner.colourkiste.math.Vec2;

public abstract class Hitbox extends EntityComponent {
    public abstract boolean contains(final Vec2 point);

//    Point2D.Double getBounds();
}
