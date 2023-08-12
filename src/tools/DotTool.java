package tools;
import java.awt.Color;

import commands.ICommand;
import commands.SetColorCommand;
import rendering.Texture;

public class DotTool extends ToolAdapter
{
    public DotTool() {
    	super("Dot");
    }

    public ICommand<Texture> use(Texture workpiece, int x, int y) {
        return new SetColorCommand(x, y, getActiveColor());
    }
    
    public ICommand<Texture> finishUsage(Texture workpiece, int x, int y) {
        return new SetColorCommand(x, y, getActiveColor());
    }
}
