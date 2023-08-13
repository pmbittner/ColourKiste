package de.bittner.colourkiste.workspace.tools;

import java.util.ArrayList;
import java.util.List;

import de.bittner.colourkiste.event.EventHandler;
import de.bittner.colourkiste.workspace.Workspace;

public class ToolBox {
	public final EventHandler<Tool> OnToolChanged = new EventHandler<>();

    private Tool currentTool;
    
    private Workspace currentWorkspace;
    
    public ToolBox() {
        currentWorkspace = null;
        setTool(NullTool.Instance);
    }
    
    public void setTool(Tool tool) {
    	if (currentTool == tool) return;
    	
    	if (currentTool != null)
    		currentTool.setWorkspace(null);
    	
    	if (tool == null)
    		tool = NullTool.Instance;
    	
        currentTool = tool;
        currentTool.setWorkspace(currentWorkspace);

		OnToolChanged.fire(currentTool);
    }

    public Tool getTool() {
        return currentTool;
    }

	public void setCurrentWorkspace(Workspace workspace) {
		currentWorkspace = workspace;
		if (currentTool != null)
			currentTool.setWorkspace(currentWorkspace);
	}
}
