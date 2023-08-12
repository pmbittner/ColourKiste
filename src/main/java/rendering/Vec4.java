package rendering;

import java.awt.*;

public record Vec4(float x, float y, float z, float w) {
    public static Vec4 all(float val) {
        return new Vec4(val, val, val, val);
    }

    public static Vec4 fromColor(Color color) {
        return new Vec4(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Vec4 scale(float s, Vec4 color) {
        return new Vec4(
                s * color.x(),
                s * color.y(),
                s * color.z(),
                s * color.w());
    }

    public static Vec4 add(Vec4 a, Vec4 b) {
        return new Vec4(
                a.x() + b.x(),
                a.y() + b.y(),
                a.z() + b.z(),
                a.w() + b.w()
        );
    }

    public static int clamp(int min, int max, int v) {
        if (v < min) return min;
        return Math.min(v, max);
    }

    public static int clampChannel(int colorChannel) {
        return clamp(0, 255, colorChannel);
    }

    public static int clampChannel(float colorChannel) {
        return clampChannel((int) colorChannel);
    }

    public Color toColor() {
        return new Color(
                clampChannel(x),
                clampChannel(y),
                clampChannel(z),
                clampChannel(w)
        );
    }
}
