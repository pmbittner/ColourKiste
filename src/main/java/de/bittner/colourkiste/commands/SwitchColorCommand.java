package de.bittner.colourkiste.commands;
import java.awt.Color;
import java.awt.image.BufferedImage;

import de.bittner.colourkiste.rendering.Texture;

public class SwitchColorCommand implements ICommand<Texture>
{
    private final Color from, to;
    private BufferedImage textureBefore = null;

    public SwitchColorCommand(Color from, Color to) {
        this.from = from;
        this.to = to;
    }

    public boolean execute(Texture t) {
        textureBefore = t.getAwtImage();
        t.setAwtImage(t.changeColor(from, to).getAwtImage());
        return true;
    }

    public void undo(Texture t) {
        if (textureBefore != null)
            t.setAwtImage(textureBefore);
    }
}
