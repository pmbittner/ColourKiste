package de.bittner.colourkiste.workspace.tools;

import java.util.ArrayList;
import java.util.List;

import de.bittner.colourkiste.workspace.Workspace;

public class ToolBox {
	public interface ToolChangedListener {
		void onToolChanged(Tool newTool);
	}

    private Tool currentTool;
    private final List<ToolChangedListener> toolChangedListeners;
    
    private Workspace currentWorkspace;
    
    public ToolBox() {
        toolChangedListeners = new ArrayList<>();
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
        
        for (ToolChangedListener l : toolChangedListeners)
        	l.onToolChanged(currentTool);
    }

    public Tool getTool() {
        return currentTool;
    }

	public void addToolChangedListener(ToolChangedListener listener) {
		if (!toolChangedListeners.contains(listener))
			toolChangedListeners.add(listener);
	}

	public boolean removeToolChangedListener(ToolChangedListener listener) {
		return toolChangedListeners.remove(listener);
	}

	public void setCurrentWorkspace(Workspace workspace) {
		currentWorkspace = workspace;
		if (currentTool != null)
			currentTool.setWorkspace(currentWorkspace);
	}
}
