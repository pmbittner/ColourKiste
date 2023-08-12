package de.bittner.colourkiste.engine.input;

import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Camera;

public final class CameraDragAndDrop extends MouseDragListener {
    private Vec2 camDelta;

    public CameraDragAndDrop(int dragAndDropMouseButton) {
        super(dragAndDropMouseButton);
    }

    @Override
    protected void dragStart(Vec2 windowPos) {
        final Camera c = getScreen().getCamera();
        camDelta = windowPos.minus(c.getLocation());
    }

    @Override
    protected void dragUpdate(Vec2 windowPos) {
        getScreen().getCamera().setLocation(windowPos.minus(camDelta));
        getScreen().refresh();
    }

    @Override
    protected void dragEnd() {

    }
}
