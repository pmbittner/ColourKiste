package de.bittner.colourkiste.workspace;

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


	boolean trySaveAs(Workspace workspace);

	boolean trySave(Workspace workspace);

	void saveAs(Workspace workspaceTab) throws IOException;
	
	void save(Workspace workspaceTab) throws IOException;
}
