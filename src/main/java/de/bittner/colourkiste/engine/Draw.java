package de.bittner.colourkiste.engine;

import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.math.geometry.Box;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Draw {
    public static void borderedShape(
            RenderTarget target,
            final Box borderBox,
            Function<Box, Box> innerTransform,
            Color fillColor,
            Color borderColor,
            Consumer<Box> draw
    ) {
        // The following works only if there was no rotation in the translation (i.e., it is rotation unaware).
//        final Box borderBox = new Box(width, height);
        target.pushColor(borderColor);
        draw.accept(borderBox);
        target.popColor();

        target.pushColor(fillColor);
        draw.accept(innerTransform.apply(borderBox));
        target.popColor();
    }

    public static void borderedShapeRelative(
            RenderTarget target,
            Box b,
            double relativeBorderWidth,
            Color fillColor,
            Color borderColor,
            Consumer<Box> draw
    ) {
        borderedShape(
                target, b,
                box -> box.shrink(new Vec2(relativeBorderWidth * box.getWidth(), relativeBorderWidth * box.getHeight())),
                fillColor, borderColor, draw
        );
    }

    public static void borderedShapeAbsolute(
            RenderTarget target,
            Box b,
            double absoluteBorderWidth,
            Color fillColor,
            Color borderColor,
            Consumer<Box> draw
    ) {
        borderedShape(
                target, b,
                box -> box.shrink(Vec2.all(absoluteBorderWidth)),
                fillColor, borderColor, draw
        );
    }

    public static void fillRect(RenderTarget target, final Box box) {
        target.getTarget().fillRect(
                (int)Math.round(box.upperLeft().x()),
                (int)Math.round(box.upperLeft().y()),
                (int)Math.round(box.getWidth()),
                (int)Math.round(box.getHeight())
                );
    }
}
