package tools;

import java.awt.Color;

import commands.FillCommand;
import commands.ICommand;
import commands.SetColorCommand;
import rendering.Texture;

public class FillTool extends ToolAdapter
{
    private final Color color;
    
    public FillTool(Color color) {
    	this(color, "Fill");
    }

    public FillTool(Color color, String name) {
        super(name);
        this.color = color;
    }

    public ICommand<Texture> use(Texture workpiece, int x, int y) {
        return new FillCommand(x, y, color);
    }
    
    public ICommand<Texture> finishUsage(Texture workpiece, int x, int y) {
        return new FillCommand(x, y, color);
    }
}
