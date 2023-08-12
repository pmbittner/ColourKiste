package de.bittner.colourkiste.tools;
import de.bittner.colourkiste.commands.ICommand;
import de.bittner.colourkiste.gui.Workspace;
import de.bittner.colourkiste.rendering.Texture;

public interface Tool
{
	public void setWorkspace(Workspace panel);
	
    public ICommand<Texture> use(Texture workpiece, int x, int y);
    
    public void startUsage(Texture workpiece, int x, int y);
    
    public void updateUsage(Texture workpiece, int x, int y);
    
    public ICommand<Texture> finishUsage(Texture workpiece, int x, int y);
    
    public void abortUsage();
    
    public String getName();
}