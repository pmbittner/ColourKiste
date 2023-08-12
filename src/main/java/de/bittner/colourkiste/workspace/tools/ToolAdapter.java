package de.bittner.colourkiste.workspace.tools;
import de.bittner.colourkiste.workspace.commands.ICommand;
import de.bittner.colourkiste.workspace.Workspace;
import de.bittner.colourkiste.rendering.Texture;

import java.awt.*;

public abstract class ToolAdapter implements Tool
{
	private Workspace workspace;
	private final String name;
	
	public ToolAdapter(String name) {
		this.name = name;
	}
	
	public void setWorkspace(Workspace panel) {
		workspace = panel;
	}
	
	protected Workspace getWorkspace() {
		return workspace;
	}
	
	protected Color getActiveColor() {
		return getWorkspace().getMainFrame().getColor();
	}
	
    public ICommand<Texture> use(Texture workpiece, int x, int y) { return null; }
    
    public void startUsage(Texture workpiece, int x, int y) {}
    
    public void updateUsage(Texture workpiece, int x, int y) {}
    
    public ICommand<Texture> finishUsage(Texture workpiece, int x, int y) { return null; }

    public void abortUsage() {}
    
    public String getName() {
    	return name;
    }
}
