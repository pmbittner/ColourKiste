package de.bittner.colourkiste.engine.components.graphics;

import de.bittner.colourkiste.engine.Draw;
import de.bittner.colourkiste.engine.RenderTarget;
import de.bittner.colourkiste.engine.components.hitbox.RectangleHitbox;
import de.bittner.colourkiste.math.Vec2;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class FilledRectangleGraphics extends EntityGraphics {
    private Color color;

    public FilledRectangleGraphics(Color color) {
        this.color = color;
    }

    @Override
    protected void refreshRelativeTransform(final AffineTransform transform) {}

    @Override
    public void draw(RenderTarget screen) {
        final RectangleHitbox r = getEntity().require(RectangleHitbox.class);
        screen.pushColor(color);
        Draw.fillRect(screen, r.getBox());
        screen.popColor();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
