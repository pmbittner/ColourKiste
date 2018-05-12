package rendering;

public class WorkingElement
{
    private Texture texture;
    private double x, y;
    
    public WorkingElement(Texture texture) {
        this.texture = texture;
    }
    
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    
    public double getX() { return x; }
    
    public double getY() { return y; }
    
    public Texture getTexture() {
        return texture;
    }
}
