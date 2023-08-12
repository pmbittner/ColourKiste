package de.bittner.colourkiste.rendering;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

@Deprecated
public abstract class WorkingElement
{
    private double x, y;
    protected AffineTransform relativeTransform;
    
    public WorkingElement() {
    	x = 0;
    	y = 0;
    	relativeTransform = new AffineTransform();
    }
    
    public AffineTransform getRelativeTransform() {
    	return relativeTransform;
    }
    
    public AffineTransform getAbsoluteTransform(AffineTransform parentTransform) {
    	AffineTransform absoluteTransform = new AffineTransform(parentTransform);
		absoluteTransform.concatenate(this.relativeTransform);
		return absoluteTransform;
    }
    
    public void updateTransform() {
    	relativeTransform.setTransform(
            1, 0,
            0, 1,
            x, y);
    }
    
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
        updateTransform();
    }
    
    public double getX() { return x; }
    
    public double getY() { return y; }
    
    public abstract void draw(Graphics2D graphics, AffineTransform parentTransform);
}
