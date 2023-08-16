package de.bittner.colourkiste.workspace.commands.resize;

import de.bittner.colourkiste.engine.Camera;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.workspace.commands.UndoableTextureManipulation;

import java.awt.*;

public abstract class ResizeCommand extends UndoableTextureManipulation {
    private final Color fillColor;
    private final Camera camera;
    private Vec2 camDelta;

    protected ResizeCommand(Camera camera) {
        this.camera = camera;
        this.fillColor = Color.WHITE;
    }

    @Override
    public void manipulate(final Texture texture) {
        this.camDelta = resizeAndGetCameraDelta(texture);

        final Vec2 camLocation = camera.getLocation();
        camera.setLocation(camLocation.add(camDelta.scale(camera.getZoom())));
    }

    @Override
    public void undo(Texture t) {
        super.undo(t);

        final Vec2 camLocation = camera.getLocation();
        camera.setLocation(camLocation.minus(camDelta.scale(camera.getZoom())));
    }

    protected abstract Vec2 resizeAndGetCameraDelta(Texture texture);

    protected Texture createResizedTexture(int w, int h) {
        final Texture t = new Texture(w, h);
        t.setColor(fillColor);
        t.fill();
        return t;
    }
}
