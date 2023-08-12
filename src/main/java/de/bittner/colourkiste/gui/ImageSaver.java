package de.bittner.colourkiste.gui;

import de.bittner.colourkiste.workspace.Workspace;

import java.io.IOException;

public interface ImageSaver {
	enum SaveResult {
		SAVED,
		DISCARDED,
		ERROR,
		ABORT;
	}

	SaveResult showSavingPrompt(Workspace workspace);

	void saveAs(Workspace workspaceTab) throws IOException;
	
	void save(Workspace workspaceTab) throws IOException;
}
