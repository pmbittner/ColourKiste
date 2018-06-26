package tools;

import java.util.ArrayList;
import java.util.List;

import gui.Workspace;

public class ToolBox {
	public interface ToolChangedListener {
		void onToolChanged(Tool newTool);
	}

    private Tool currentTool;
    private List<ToolChangedListener> toolChangedListeners;
    
    private Workspace currentWorkspace;
    
    public ToolBox() {
        toolChangedListeners = new ArrayList<>();
        currentWorkspace = null;
        setTool(NullTool.Instance);
    }
    
    public void setTool(Tool tool) {
    	if (currentTool != null)
    		currentTool.setImagePanel(null);
    	
    	if (tool == null)
    		tool = NullTool.Instance;
    	
        currentTool = tool;
        currentTool.setImagePanel(currentWorkspace);
        
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

	public void setCurrentWorkspace(Workspace imagePanel) {
		currentWorkspace = imagePanel;
		if (currentTool != null)
			currentTool.setImagePanel(currentWorkspace);
	}
}
