package de.bittner.colourkiste.gui.menu;

import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import de.bittner.colourkiste.gui.ImageSaver;
import de.bittner.colourkiste.gui.MainFrame;
import de.bittner.colourkiste.gui.Workspace;
import de.bittner.colourkiste.gui.WorkspaceTab;
import de.bittner.colourkiste.gui.io.SaveImageFileDialog;
import de.bittner.colourkiste.rendering.Texture;

public class SaveMenuBarItem implements MenuBarItem, ImageSaver {

    private MainFrame frame;
	private JMenuItem saveMenuItem, saveAsMenuItem;
	
	@Override
	public void create(MainFrame frame) {
        this.frame = frame;

		JMenu fileMenu = frame.getMenuWithName("File");
		
		saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.addActionListener(evt -> save(frame.getCurrentWorkspaceTab().getWorkspace()));
        fileMenu.add(saveMenuItem);

        saveAsMenuItem = new JMenuItem("Save as");
        saveAsMenuItem.addActionListener(evt -> saveAs(frame.getCurrentWorkspaceTab().getWorkspace()));
        fileMenu.add(saveAsMenuItem);
	}

	@Override
	public void update(MainFrame frame) {
        boolean savePossible = false;
        if (frame.getCurrentWorkspaceTab() != null) {
        	savePossible = frame.getCurrentWorkspaceTab().getWorkspace().getWorkingFile() != null;
        }
        saveMenuItem.setEnabled(savePossible);
        saveAsMenuItem.setEnabled(savePossible);
	}

    @Override
    public void showSavingPrompt(Workspace workspace) {
        int n = JOptionPane.showConfirmDialog(
                frame,
                "All unsaved changes of the current image will be lost.\nWould you like to save first?",
                "SAVEty first!",
                JOptionPane.YES_NO_OPTION);
        if(n == JOptionPane.YES_OPTION)
            save(workspace);
    }

    public File showSaveDialog(Workspace workspace) {
        SaveImageFileDialog sd = new SaveImageFileDialog(frame, workspace.getWorkingFile());
        return sd.getFile();
    }

    @Override
    public void saveAs(Workspace workspace) {
        File saveFile = showSaveDialog(workspace);
        if (saveFile != null) {
        	workspace.reassignWorkingFile(saveFile);
            save(workspace);
        }
    }

    @Override
    public void save(Workspace workspace) {
        if (workspace.getWorkingFile() == null)
            saveAs(workspace);
        else
            Texture.saveAsPng(workspace.getTexture(), workspace.getWorkingFile());
    }
}
