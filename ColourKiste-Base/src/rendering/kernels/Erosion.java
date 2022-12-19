package rendering.kernels;

import rendering.Kernel;
import rendering.Texture;

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
        for (int ky = -kernelLen; ky <= kernelLen && t.inHeight(ky + y); ++ky) {
            for (int kx = -kernelLen; kx <= kernelLen && t.inWidth(kx + x); ++kx) {
                if (t.getColorAt(kx + x, ky + y).getBlue() == 0) {
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
