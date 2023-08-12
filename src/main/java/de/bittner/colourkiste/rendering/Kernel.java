package de.bittner.colourkiste.rendering;

import java.awt.*;

@FunctionalInterface
public interface Kernel {
    Color foldAt(Texture t, int x, int y);

    default Texture apply(Texture t) {
        return t.convolution(this);
    }
}
