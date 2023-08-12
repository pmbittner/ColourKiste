package de.bittner.colourkiste.imageprocessing.kernels;

import de.bittner.colourkiste.imageprocessing.Kernel;
import de.bittner.colourkiste.rendering.Texture;

import java.awt.*;

public record Erosion(int kernelsize, int offset) implements Kernel {
    public static Erosion Erosion() {
        return new Erosion(-1, 6);
    }

    public static Erosion Dilatation() {
        return new Erosion(-1, 3);
    }

    @Override
    public Color foldAt(Texture t, int x, int y) {
        int blackNeighbors = 0;
        int kernelLen = 1;
        for (int ky = -kernelLen; ky <= kernelLen; ++ky) {
            for (int kx = -kernelLen; kx <= kernelLen; ++kx) {
                if (t.inHeight(ky + y)  && t.inWidth(kx + x)  && t.getColorAt(kx + x, ky + y).getBlue() == 0) {
                    ++blackNeighbors;
                }
            }
        }

        if (blackNeighbors >= offset) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }
}
