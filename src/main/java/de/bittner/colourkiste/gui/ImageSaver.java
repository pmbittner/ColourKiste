package de.bittner.colourkiste.gui;

import de.bittner.colourkiste.workspace.Workspace;

public interface ImageSaver {
	void showSavingPrompt(Workspace workspace);

	void saveAs(Workspace workspaceTab);
	
	void save(Workspace workspaceTab);
}
