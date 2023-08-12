package de.bittner.colourkiste.commands;
import java.awt.Color;

import de.bittner.colourkiste.rendering.Texture;

public class SetColorCommand implements ICommand<Texture>
{
    private final int x;
    private final int y;
    private final Color color;
    private Color colorBefore;
    
    public SetColorCommand(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    public boolean execute(Texture t) {
        colorBefore = t.getColorAt(x, y);
        t.setColorAt(x, y, color);
        return true;
    }
    
    public void undo(Texture t) {
        t.setColorAt(x, y, colorBefore);
    }
}
