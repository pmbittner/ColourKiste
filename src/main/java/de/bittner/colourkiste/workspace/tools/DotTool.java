package de.bittner.colourkiste.workspace.tools;

import de.bittner.colourkiste.workspace.ICommand;
import de.bittner.colourkiste.workspace.commands.SetColorCommand;
import de.bittner.colourkiste.rendering.Texture;

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
