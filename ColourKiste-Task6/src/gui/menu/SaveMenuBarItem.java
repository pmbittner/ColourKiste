package gui.menu;

import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gui.ImageSaver;
import gui.MainFrame;
import gui.WorkspaceTab;
import gui.io.SaveImageFileDialog;
import rendering.Texture;

public class SaveMenuBarItem implements MenuBarItem, ImageSaver {
	
	JMenuItem saveMenuItem, saveAsMenuItem;
	
	@Override
	public void create(MainFrame frame) {
		JMenu fileMenu = frame.getMenuWithName("File");
		
		saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    save(frame.getCurrentWorkspaceTab());
                }
            });
        fileMenu.add(saveMenuItem);

        saveAsMenuItem = new JMenuItem("Save as");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    saveAs(frame.getCurrentWorkspaceTab());
                }
            });
        fileMenu.add(saveAsMenuItem);
	}

	@Override
	public void update(MainFrame frame) {
        boolean savePossible = false;
        if (frame.getCurrentWorkspaceTab() != null) {
        	savePossible = frame.getCurrentWorkspaceTab().getWorkingFile() != null;
        }
        saveMenuItem.setEnabled(savePossible);
        saveAsMenuItem.setEnabled(savePossible);
	}

    public void showSavingPrompt(WorkspaceTab workspaceTab) {
        int n = JOptionPane.showConfirmDialog(
        		workspaceTab.getFrame(),
                "All unsaved changes of the current image will be lost.\nWould you like to save first?",
                "SAVEty first!",
                JOptionPane.YES_NO_OPTION);
        if(n == JOptionPane.YES_OPTION)
            save(workspaceTab);
    }

    public File showSaveDialog(WorkspaceTab workspaceTab) {
        SaveImageFileDialog sd = new SaveImageFileDialog(workspaceTab.getFrame(), workspaceTab.getWorkingFile());
        return sd.getFile();
    }


    public void saveAs(WorkspaceTab workspaceTab) {
        File saveFile = showSaveDialog(workspaceTab);
        if (saveFile != null) {
        	workspaceTab.reassignWorkingFile(saveFile);
            save(workspaceTab);
        }
    }

    public void save(WorkspaceTab workspaceTab) {
        if (workspaceTab.getWorkingFile() == null)
            saveAs(workspaceTab);
        else
            Texture.saveAsPng(workspaceTab.getImagePanel().getTexture(), workspaceTab.getWorkingFile());
    }
}
