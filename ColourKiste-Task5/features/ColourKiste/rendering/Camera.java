package rendering;
public class Camera
{
    private double x, y;

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }

    public double getY() { return y; }
}
