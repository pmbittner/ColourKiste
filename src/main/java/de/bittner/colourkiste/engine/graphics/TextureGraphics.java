package de.bittner.colourkiste.engine.graphics;

import de.bittner.colourkiste.engine.RenderTarget;
import de.bittner.colourkiste.math.Transform;
import de.bittner.colourkiste.rendering.Texture;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class TextureGraphics extends EntityGraphics {
    private Texture texture;

    public TextureGraphics(Texture texture) {
        super();
        setTexture(texture);
    }

    @Override
    protected void refreshRelativeTransform(final AffineTransform transform) {
        Transform.setAsBox(
                transform,
                texture == null ? 0 : texture.getWidth(),
                texture == null ? 0 : texture.getHeight()
        );
    }

    @Override
    public void draw(RenderTarget screen) {
        if (texture == null) return;
        screen.pushTransform(getRelativeTransform());
        screen.getTarget().drawImage(texture.getAwtImage(), null,0, 0);
        screen.popTransform();
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        refreshRelativeTransform();
    }

    public Texture getTexture() {
        return texture;
    }
}
