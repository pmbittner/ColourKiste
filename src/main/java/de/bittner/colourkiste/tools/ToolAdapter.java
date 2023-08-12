package de.bittner.colourkiste.tools;
import de.bittner.colourkiste.commands.ICommand;
import de.bittner.colourkiste.gui.Workspace;
import de.bittner.colourkiste.rendering.Texture;

import java.awt.*;

public abstract class ToolAdapter implements Tool
{
	private Workspace imagePanel;
	private String name;
	
	public ToolAdapter(String name) {
		this.name = name;
	}
	
	public void setImagePanel(Workspace panel) {
		imagePanel = panel;
	}
	
	protected Workspace getImagePanel() {
		return imagePanel;
	}
	
	protected Color getActiveColor() {
		return getImagePanel().getMainFrame().getColor();
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
