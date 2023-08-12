package de.bittner.colourkiste.tools;
import de.bittner.colourkiste.commands.ICommand;
import de.bittner.colourkiste.gui.Workspace;
import de.bittner.colourkiste.rendering.Texture;

public interface Tool
{
	void setWorkspace(Workspace panel);
	
    ICommand<Texture> use(Texture workpiece, int x, int y);
    
    void startUsage(Texture workpiece, int x, int y);
    
    void updateUsage(Texture workpiece, int x, int y);
    
    ICommand<Texture> finishUsage(Texture workpiece, int x, int y);
    
    void abortUsage();
    
    String getName();
}