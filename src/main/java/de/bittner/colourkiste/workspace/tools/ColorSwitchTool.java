package de.bittner.colourkiste.workspace.tools;
import java.awt.Color;

import de.bittner.colourkiste.workspace.ICommand;
import de.bittner.colourkiste.workspace.commands.SwitchColorCommand;
import de.bittner.colourkiste.rendering.Texture;

public class ColorSwitchTool extends ToolAdapter
{
	private final Color switchToColor;
	
	public ColorSwitchTool(Color color) {
    	super("Color Deleter");
    	switchToColor = color;
	}

	public ColorSwitchTool() {
    	this(Texture.clr_TRANSPARENT);
	}
	
    public ICommand<Texture> use(Texture t, int x, int y) {
        return new SwitchColorCommand(t.getColorAt(x, y), switchToColor);
    }
    
    public ICommand<Texture> finishUsage(Texture t, int x, int y) {
        return new SwitchColorCommand(t.getColorAt(x, y), switchToColor);
    }
}