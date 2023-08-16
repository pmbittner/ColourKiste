package de.bittner.colourkiste.workspace.elements.resize;

import de.bittner.colourkiste.engine.RenderTarget;
import de.bittner.colourkiste.engine.components.graphics.EntityGraphics;
import de.bittner.colourkiste.engine.components.hitbox.Hitbox;
import de.bittner.colourkiste.engine.components.hitbox.RectangleHitbox;
import de.bittner.colourkiste.math.Transform;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.math.geometry.Box;

import java.awt.*;
import java.awt.geom.AffineTransform;

import de.bittner.colourkiste.engine.Draw;
import org.variantsync.functjonal.Curry;

public class ResizeBarGraphics extends EntityGraphics {
    @Override
    protected void draw(RenderTarget screen) {
        // FIXME: THere is a bug in here that causes the drawn box to be shifted by half a pixel towards is rotation direction.
        //        I do not know what causes this bug
        Box hitbox = getEntity().require(RectangleHitbox.class).getBox();
        final ResizeBar resizeBar = getEntity().require(ResizeBar.class);

        int alpha = 180;
        if (resizeBar.isPressed()) {
            alpha = (int) (0.7 * alpha);
        }

////        final double shiftX = 0.5 * ((int) hitbox.getHeight() % 2);
//        final double shiftX = 0.5;
//
//        hitbox = new Box(
//                new Vec2(hitbox.upperLeft().x(), hitbox.upperLeft().y() - shiftX),
//                new Vec2(hitbox.lowerRight().x(), hitbox.lowerRight().y() - shiftX)
//        );

        final double invZoom = 1.0 / screen.getCamera().getZoom();
        final AffineTransform unZoom = AffineTransform.getScaleInstance(invZoom, 1);
        screen.pushTransform(unZoom);

        Draw.borderedShapeAbsolute(
                screen, hitbox, 3,
                new Color (23, 222, 222, alpha),
                new Color (0, 0, 0, alpha),
                Curry.curry(Draw::fillRect).apply(screen)
        );

        screen.popTransform();
    }

    @Override
    protected void refreshRelativeTransform(AffineTransform transform) {}
}
