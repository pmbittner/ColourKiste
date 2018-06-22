package commands;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import rendering.Texture;

public class CutRectangleCommand implements ICommand<Texture>
{
    private Rectangle2D.Double rect;
    private Texture textureBefore;
    
    public CutRectangleCommand(Rectangle2D.Double rect) {
        this.rect = rect;
    }

    public boolean execute(Texture t) {
    	if (t != null) {
	        textureBefore = new Texture(t);
	        Texture cut = t.cut((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
	        return true;
    	}
    	return false;
    }

    public void undo(Texture t) {
        if (textureBefore != null)
            t.setAwtImage(textureBefore.getAwtImage());
    }

}
