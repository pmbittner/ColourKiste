package de.bittner.colourkiste.engine.input;

import de.bittner.colourkiste.engine.InputListener;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.engine.Camera;

import java.awt.event.MouseWheelEvent;

public class ZoomViaMouseWheel extends InputListener {
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        final Camera c = getScreen().getCamera();
        c.zoomTowards(
                -e.getWheelRotation(),
                getScreen().screenToViewportCoord(new Vec2(e.getX(), e.getY()))
        );
        getScreen().refresh();
    }
}
