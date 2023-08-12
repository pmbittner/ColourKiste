package de.bittner.colourkiste.imageprocessing;

import de.bittner.colourkiste.rendering.Texture;

import java.awt.*;

@FunctionalInterface
public interface Kernel {
    Color foldAt(Texture t, int x, int y);

    default Texture apply(Texture t) {
        return t.convolution(this);
    }
}
