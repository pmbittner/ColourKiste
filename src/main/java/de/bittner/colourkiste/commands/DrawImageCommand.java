package de.bittner.colourkiste.commands;
import java.awt.image.BufferedImage;

import de.bittner.colourkiste.rendering.Texture;

public class DrawImageCommand implements ICommand<Texture>
{
    private int x, y;
    private Texture texture;
    private BufferedImage textureBefore = null;
    
    public DrawImageCommand(Texture texture, int x, int y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public boolean execute(Texture t) {
        textureBefore = t.getAwtImage();
        t.setAwtImage(new Texture(t).getAwtImage());
        t.drawTexture(texture, x, y);
        return true;
    }

    public void undo(Texture t) {
        if (textureBefore != null)
            t.setAwtImage(textureBefore);
    }
}
