package de.bittner.colourkiste.workspace.commands.resize;

import de.bittner.colourkiste.engine.Camera;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Texture;

public class ResizeUpCommand extends ResizeCommand {
    private final int deltaHeight;

    public ResizeUpCommand(final Camera camera, int deltaHeight) {
        super(camera);
        this.deltaHeight = deltaHeight;
    }

    @Override
    public Vec2 resizeAndGetCameraDelta(Texture texture) {
        final int newHeight = texture.getHeight() + deltaHeight;
        final Texture t = createResizedTexture(texture.getWidth(), newHeight);
        t.drawTexture(texture, 0, deltaHeight);
        texture.setAwtImage(t.getAwtImage());

        return new Vec2(0, - deltaHeight / 2.0);
    }
}
