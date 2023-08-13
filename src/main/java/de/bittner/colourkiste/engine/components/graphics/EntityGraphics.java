package de.bittner.colourkiste.engine.components.graphics;

import de.bittner.colourkiste.engine.EntityComponent;
import de.bittner.colourkiste.engine.RenderTarget;
import org.variantsync.functjonal.Lazy;

import java.awt.geom.AffineTransform;

public abstract class EntityGraphics extends EntityComponent {
    private final AffineTransform affineTransform = new AffineTransform();
    private final Lazy<AffineTransform> relativeTransform = Lazy.of(() -> {
        refreshRelativeTransform(affineTransform);
        return affineTransform;
    });

    public EntityGraphics() {}

    public final void render(final RenderTarget screen) {
        screen.pushTransform(getEntity().getRelativeTransform());
        draw(screen);
        screen.popTransform();
    }

    protected abstract void draw(RenderTarget screen);

    protected abstract void refreshRelativeTransform(final AffineTransform transform);

    public void refreshRelativeTransform() {
        relativeTransform.forget();
    }

    public final AffineTransform getRelativeTransform() {
        return relativeTransform.run();
    }
}