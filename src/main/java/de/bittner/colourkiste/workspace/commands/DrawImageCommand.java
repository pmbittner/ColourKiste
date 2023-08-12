package de.bittner.colourkiste.workspace.commands;
import java.awt.image.BufferedImage;

import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.workspace.ICommand;

public class DrawImageCommand implements ICommand<Texture>
{
    private final int x;
    private final int y;
    private final Texture texture;
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
