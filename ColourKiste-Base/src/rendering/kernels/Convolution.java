package rendering.kernels;

import rendering.Kernel;
import rendering.Matrix3x3;
import rendering.Texture;
import rendering.Vec4;

import java.awt.*;

public class Convolution implements Kernel {
    private final Matrix3x3 kernel;

    public Convolution(Matrix3x3 kernel) {
        this.kernel = kernel;
    }

    @Override
    public Color foldAt(Texture t, int x, int y) {
        Vec4 conv = Vec4.all(0);

        for (int ky = -1; ky <= 1; ++ky) {
            for (int kx = -1; kx <= 1; ++kx) {
                if (t.inHeight(ky + y) && t.inWidth(kx + x)) {
                    conv = Vec4.add(
                            Vec4.scale(
                                    kernel.getCell(kx + 1, ky + 1),
                                    Vec4.fromColor(t.getColorAt(kx + x, ky + y))
                            ),
                            conv
                    );
                }
            }
        }

        //System.out.println(conv);
        return conv.toColor();
    }

    public static Convolution smoothing() {
        /*
        float eighteenth = 1f / 18f;
        float corner =     eighteenth;
        float edge = 2 * eighteenth;
        float center = 6 * eighteenth;
        */
        float inv36 = 1f / 36f;
        float corner = 0 * inv36;
        float edge = 2 * inv36;
        float center = 28 * inv36;

        return new Convolution(new Matrix3x3(
                corner,  edge , corner,
                 edge , center,  edge,
                corner,  edge , corner
        ));
    }
}
