package de.bittner.colourkiste.tools;

import de.bittner.colourkiste.commands.FillCommand;
import de.bittner.colourkiste.commands.ICommand;
import de.bittner.colourkiste.rendering.Texture;

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
