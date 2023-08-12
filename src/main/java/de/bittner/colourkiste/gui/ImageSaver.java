package de.bittner.colourkiste.gui;

public interface ImageSaver {
	void showSavingPrompt(Workspace workspace);

	void saveAs(Workspace workspaceTab);
	
	void save(Workspace workspaceTab);
}
