package de.bittner.colourkiste.engine;

import de.bittner.colourkiste.math.Vec2;

public interface Screen {
    Camera getCamera();

    default void addInputListener(final InputListener inputListener) {
        inputListener.setScreen(this);
    }

    default void removeInputListener(final InputListener inputListener) {
        inputListener.setScreen(null);
    }

    Vec2 screenToViewportCoord(final Vec2 vec2);

    void refresh();
}
