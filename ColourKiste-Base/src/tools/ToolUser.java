package tools;

public interface ToolUser {
	public interface ToolChangedListener {
		void onToolChanged(Tool newTool);
	}
	
	public void setTool(Tool tool);
	
	public Tool getTool();
	
	void addToolChangedListener(ToolChangedListener listener);
	boolean removeToolChangedListener(ToolChangedListener listener);
}
