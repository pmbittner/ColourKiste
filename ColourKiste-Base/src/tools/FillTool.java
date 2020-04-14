package tools;

import java.awt.Color;

import commands.FillCommand;
import commands.ICommand;
import commands.SetColorCommand;
import rendering.Texture;

public class FillTool extends ToolAdapter
{
    private Color color;
    
    public FillTool(Color color) {
    	super("Fill");
        this.color = color;
    }

    public ICommand<Texture> use(Texture workpiece, int x, int y) {
        return new FillCommand(x, y, color);
    }
    
    public ICommand<Texture> finishUsage(Texture workpiece, int x, int y) {
        return new FillCommand(x, y, color);
    }
}
