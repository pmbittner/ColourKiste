package de.bittner.colourkiste.engine.components;

import de.bittner.colourkiste.engine.EntityComponent;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class EntityGraphics extends EntityComponent {

    public EntityGraphics() {}

    public abstract void draw(Graphics2D screen, AffineTransform parentTransform);
}