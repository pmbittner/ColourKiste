package rendering;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class TextureWorkingElement extends WorkingElement {
    private Texture texture;
    
	public TextureWorkingElement(Texture texture) {
		super();
		this.texture = texture;
	}
	
	@Override
	public void updateTransform() {
		int w = 0;
		int h = 0;
		
		if (texture != null) {
			w = texture.getWidth();
			h = texture.getHeight();
		}
		
    	double
        xpos = getX() - w / 2,
        ypos = getY() - h / 2;
        
    	relativeTransform.setTransform(
            1, 0,
            0, 1,
            xpos,
            ypos);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        updateTransform();
    }
    
    public Texture getTexture() {
       return texture;
    }

	@Override
	public void draw(Graphics2D graphics, AffineTransform parentTransform) {
		graphics.drawImage(texture.getAwtImage(), getAbsoluteTransform(parentTransform), null);
	}
}
