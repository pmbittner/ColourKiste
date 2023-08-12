package de.bittner.colourkiste.gui.menu;

import de.bittner.colourkiste.gui.ImageSaver;
import de.bittner.colourkiste.gui.MainFrame;
import de.bittner.colourkiste.workspace.Workspace;
import de.bittner.colourkiste.gui.io.SaveImageFileDialog;
import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.workspace.remember.RememberFileSave;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SaveMenuBarItem implements MenuBarItem, ImageSaver {
    private MainFrame frame;
	private JMenuItem saveMenuItem, saveAsMenuItem;
	
	@Override
	public void create(MainFrame frame) {
        this.frame = frame;

		JMenu fileMenu = frame.getMenuWithName("File");
		
		saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.addActionListener(evt -> trySave(frame.getCurrentWorkspaceTab().getWorkspace()));
        fileMenu.add(saveMenuItem);

        saveAsMenuItem = new JMenuItem("Save as");
        saveAsMenuItem.addActionListener(evt -> trySaveAs(frame.getCurrentWorkspaceTab().getWorkspace()));
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
    public SaveResult showSavingPrompt(Workspace workspace) {
        final int n = JOptionPane.showConfirmDialog(
                frame,
                "All unsaved changes of the current image will be lost.\nWould you like to save first?",
                "SAVEty first!",
                JOptionPane.YES_NO_CANCEL_OPTION);

        return switch (n) {
            case JOptionPane.CANCEL_OPTION -> SaveResult.ABORT;
            case JOptionPane.YES_OPTION -> {
                if (trySave(workspace)) {
                    yield SaveResult.SAVED;
                } else {
                    yield SaveResult.ERROR;
                }
            }
            default -> SaveResult.DISCARDED;
        };
    }

    private File showSaveDialog(Workspace workspace) {
        SaveImageFileDialog sd = new SaveImageFileDialog(frame, workspace.getWorkingFile());
        return sd.getFile();
    }

    public static void informUserThatFileSaveFailed(final Component component) {
        JOptionPane.showMessageDialog(
                component,
                "All unsaved changes of the current image will be lost.\nWould you like to save first?",
                "Error upon file save!",
                JOptionPane.ERROR_MESSAGE);
    }

    public boolean trySaveAs(Workspace workspace) {
        try {
            saveAs(workspace);
            return true;
        } catch (final IOException e) {
            informUserThatFileSaveFailed(frame);
            return false;
        }
    }

    public boolean trySave(Workspace workspace) {
        try {
            save(workspace);
            return true;
        } catch (final IOException e) {
            informUserThatFileSaveFailed(frame);
            return false;
        }
    }

    @Override
    public void saveAs(Workspace workspace) throws IOException {
        final File saveFile = showSaveDialog(workspace);
        if (saveFile != null) {
        	workspace.reassignWorkingFile(saveFile);
            save(workspace);
        }
    }

    @Override
    public void save(Workspace workspace) throws IOException {
        if (workspace.getWorkingFile() == null) {
            saveAs(workspace);
        } else {
            workspace.save();
        }
    }
}
