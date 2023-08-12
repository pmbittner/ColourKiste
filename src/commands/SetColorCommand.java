package commands;
import java.awt.Color;

import rendering.Texture;

public class SetColorCommand implements ICommand<Texture>
{
    private int x, y;
    private Color color, colorBefore;
    
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
