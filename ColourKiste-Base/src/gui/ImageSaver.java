package gui;

public interface ImageSaver {
	void showSavingPrompt(WorkspaceTab workspaceTab);

	public void saveAs(WorkspaceTab workspaceTab);
	
	public void save(WorkspaceTab workspaceTab);
}
