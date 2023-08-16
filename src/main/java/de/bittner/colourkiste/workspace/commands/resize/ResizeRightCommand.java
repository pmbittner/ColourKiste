package de.bittner.colourkiste.workspace.commands.resize;

import de.bittner.colourkiste.engine.Camera;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Texture;

public class ResizeRightCommand extends ResizeCommand {
    private final int deltaWidth;

    public ResizeRightCommand(final Camera camera, int deltaWidth) {
        super(camera);
        this.deltaWidth = deltaWidth;
    }

    @Override
    public Vec2 resizeAndGetCameraDelta(Texture texture) {
        final int newWidth = texture.getWidth() + deltaWidth;
        final Texture t = createResizedTexture(newWidth, texture.getHeight());
        t.drawTexture(texture, 0, 0);
        texture.setAwtImage(t.getAwtImage());

        return new Vec2(deltaWidth / 2.0, 0);
    }
}
