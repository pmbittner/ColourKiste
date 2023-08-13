package de.bittner.colourkiste.engine.components.hitbox;

import de.bittner.colourkiste.engine.components.graphics.TextureGraphics;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.math.geometry.Box;
import de.bittner.colourkiste.rendering.Texture;

public class TextureHitbox extends Hitbox {
    @Override
    public boolean contains(Vec2 worldPos) {
        final TextureGraphics g = getEntity().require(TextureGraphics.class);
        final Texture t = g.getTexture();
        if (t == null) return false;
        return new Box(t.getWidth(), t.getHeight()).contains(getEntity().toEntitySpace(worldPos));
    }
}
