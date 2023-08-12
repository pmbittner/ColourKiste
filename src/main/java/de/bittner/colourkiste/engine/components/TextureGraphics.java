package de.bittner.colourkiste.engine.components;

import de.bittner.colourkiste.math.Transform;
import de.bittner.colourkiste.rendering.Texture;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class TextureGraphics extends EntityGraphics {
    private Texture texture;
    private final AffineTransform transform;

    public TextureGraphics(Texture texture) {
        super();
        transform = new AffineTransform();
        setTexture(texture);
    }

    public void updateTransform() {
        int w = 0;
        int h = 0;

        if (texture != null) {
            w = texture.getWidth();
            h = texture.getHeight();
        }

        transform.setTransform(
                1, 0,
                0, 1,
                -w / 2.0,
                -h / 2.0);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        updateTransform();
    }

    public Texture getTexture() {
        return texture;
    }

    public AffineTransform getTransform() {
        return transform;
    }

    @Override
    public void draw(Graphics2D graphics, AffineTransform parentTransform) {
        if (texture == null) return;
        graphics.drawImage(
                texture.getAwtImage(),
                Transform.mult(parentTransform, getEntity().getRelativeTransform(), transform),
                null);
    }
}
