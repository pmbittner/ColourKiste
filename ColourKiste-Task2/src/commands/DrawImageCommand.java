package commands;
import java.awt.image.BufferedImage;

import rendering.Texture;

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

    public void execute(Texture t) {
        textureBefore = t.getAwtImage();
        t.setAwtImage(new Texture(t).getAwtImage());
        t.drawTexture(texture, x, y);
    }

    public void undo(Texture t) {
        if (textureBefore != null)
            t.setAwtImage(textureBefore);
    }
}
