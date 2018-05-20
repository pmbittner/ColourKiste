package tools;
import java.awt.Color;

import commands.ICommand;
import commands.SetColorCommand;
import rendering.Texture;

public class DotTool extends ToolAdapter
{
    private Color color;
    
    public DotTool(Color color){
        this.color = color;
    }

    public ICommand<Texture> use(Texture workpiece, int x, int y) {
        return new SetColorCommand(x, y, color);
    }
}
