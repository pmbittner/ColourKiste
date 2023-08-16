package de.bittner.colourkiste.engine;

import de.bittner.colourkiste.util.Assert;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Stack;

public class RenderTarget {
    private final Graphics2D screen;
    private final Stack<Color> backgrounds;
    private final Stack<Color> colors;
    private final Stack<AffineTransform> transforms;
    private final Camera camera;

    public RenderTarget(Graphics2D screen, Camera camera) {
        this.screen = screen;
        this.camera = camera;
        backgrounds = new Stack<>();
        colors = new Stack<>();
        transforms = new Stack<>();

        backgrounds.push(this.screen.getBackground());
        colors.push(this.screen.getColor());
        transforms.push(this.screen.getTransform());
//        transforms.push(new AffineTransform());
    }

    private <T> T popButNotLast(final Stack<T> t) {
        Assert.assertTrue(t.size() > 1, "Illegal pop! Stack is already empty!");
        return t.pop();
    }

    public void pushBackground(final Color color) {
        screen.setBackground(backgrounds.push(color));
    }

    public void popBackground() {
        screen.setBackground(popButNotLast(backgrounds));
    }

    public void pushColor(final Color color) {
        screen.setColor(colors.push(color));
    }

    public void popColor() {
        screen.setColor(popButNotLast(colors));
    }

    public void pushTransform(final AffineTransform t) {
        screen.transform(t);
        transforms.push(screen.getTransform());
    }

    public void popTransform() {
        popButNotLast(transforms);
        screen.setTransform(transforms.peek());
    }

    public Graphics2D getTarget() {
        return screen;
    }

    public Camera getCamera() {
        return camera;
    }
}
