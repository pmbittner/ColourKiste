package de.bittner.colourkiste.commands;

import java.awt.geom.Rectangle2D;

import de.bittner.colourkiste.rendering.Texture;

public class CutRectangleCommand implements ICommand<Texture>
{
    private final Rectangle2D.Double rect;
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
