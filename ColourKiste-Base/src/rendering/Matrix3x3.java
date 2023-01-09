package rendering;

public class Matrix3x3 {
    float[][] mat;

    public Matrix3x3() {
        mat = new float[3][3];
    }

    public Matrix3x3(float ul, float u, float ur, float cl, float c, float cr, float ll, float l, float lr) {
        this();
        mat[0][0] = ul;
        mat[1][0] = u;
        mat[2][0] = ur;
        mat[0][1] = cl;
        mat[1][1] = c;
        mat[2][1] = cr;
        mat[0][2] = ll;
        mat[1][2] = l;
        mat[2][2] = lr;
    }

    public Matrix3x3(float val) {
        this(val, val, val, val, val, val, val, val, val);
    }

    public float getCell(int x, int y) {
        if (x < 0 || x > 2) {
            throw new IllegalArgumentException("x = " + x + " is out of bounds");
        }
        if (y < 0 || y > 2) {
            throw new IllegalArgumentException("y = " + y + " is out of bounds");
        }
        return mat[x][y];
    }
}
