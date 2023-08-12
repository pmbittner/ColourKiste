package de.bittner.colourkiste.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class RectangleWorkingElement extends WorkingElement {	
    private double w, h;
    private final Color color;
    
    private final Rectangle2D.Double rect;
    
    public RectangleWorkingElement(Color color) {
    	this.color = color;
    	this.rect = new Rectangle2D.Double(0, 0, 0, 0);
    	w = 0;
    	h = 0;
    }
    
    @Override
    public void updateTransform() {
    	super.updateTransform();
    	updateRect();
    }
    
    private void updateRect() {
    	double cornerX = getX();
    	double cornerY = getY();
    	double wTemp = w;
    	double hTemp = h;
		
		if (w < 0) {
			cornerX += w;
			wTemp = -wTemp;
		}
		
		if (h < 0) {
			cornerY += h;
			hTemp = -hTemp;
		}
		
		rect.setRect(cornerX, cornerY, wTemp, hTemp);
    }
    
    public void setSize(double w, double h) {
        this.w = w;
        this.h = h;
        updateTransform();
    }
    
    public double getWidth() { return w; }
    
    public double getHeight() { return h; }
    
    public Rectangle2D.Double getRect() {
    	return rect;
    }
	
	@Override
	public void draw(Graphics2D graphics, AffineTransform parentTransform) {
		Color oldColor = graphics.getColor();
		graphics.setColor(color);		
		graphics.setTransform(parentTransform);
		graphics.drawRect((int)(rect.getX()), (int)(rect.getY()), (int)rect.getWidth(), (int)rect.getHeight());
		graphics.setColor(oldColor);
	}
}
