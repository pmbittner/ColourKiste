package de.bittner.colourkiste.workspace.tools;
import de.bittner.colourkiste.workspace.ICommand;
import de.bittner.colourkiste.workspace.Workspace;
import de.bittner.colourkiste.rendering.Texture;

public interface Tool
{
    // TODO: Use vectors

	void setWorkspace(Workspace panel);
	
    ICommand<Texture> use(Texture workpiece, int x, int y);
    
    void startUsage(Texture workpiece, int x, int y);
    
    void updateUsage(Texture workpiece, int x, int y);
    
    ICommand<Texture> finishUsage(Texture workpiece, int x, int y);
    
    void abortUsage();
    
    String getName();
}