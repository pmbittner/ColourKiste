package de.bittner.colourkiste.engine.graphics;

import de.bittner.colourkiste.engine.RenderTarget;
import de.bittner.colourkiste.engine.hitbox.RectangleHitbox;
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
        final Vec2 ul = r.getBox().upperLeft();
        final Vec2 lr = r.getBox().lowerRight();

        screen.pushColor(color);
        screen.getTarget().fillRect((int)ul.x(), (int)ul.y(), (int)(lr.x() - ul.x()), (int)(lr.y() - ul.y()));
        screen.popColor();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
