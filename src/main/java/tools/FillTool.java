package tools;

import commands.FillCommand;
import commands.ICommand;
import rendering.Texture;

public class FillTool extends ToolAdapter
{    
    public FillTool() {
    	super("Fill");
    }

    public ICommand<Texture> use(Texture workpiece, int x, int y) {
        return new FillCommand(x, y, getActiveColor());
    }
    
    public ICommand<Texture> finishUsage(Texture workpiece, int x, int y) {
        return new FillCommand(x, y, getActiveColor());
    }
}
