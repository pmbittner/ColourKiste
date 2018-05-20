package commands;
import java.awt.Color;
import java.awt.image.BufferedImage;

import rendering.Texture;

public class SwitchColorCommand implements ICommand<Texture>
{
    private Color from, to;
    private BufferedImage textureBefore = null;

    public SwitchColorCommand(Color from, Color to) {
        this.from = from;
        this.to = to;
    }

    public void execute(Texture t) {
        textureBefore = t.getAwtImage();
        t.setAwtImage(t.changeColor(from, to).getAwtImage());
    }

    public void undo(Texture t) {
        if (textureBefore != null)
            t.setAwtImage(textureBefore);
    }
}
