package gui.menu;

import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gui.ImageSaver;
import gui.MainFrame;
import gui.io.SaveImageFileDialog;
import rendering.Texture;

public class SaveMenuBarItem implements MenuBarItem, ImageSaver {
	
	JMenuItem saveMenuItem;
	
	@Override
	public void create(MainFrame frame) {
		JMenu fileMenu = frame.getMenuWithName("File");
		
		saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    save(frame);
                }
            });
        fileMenu.add(saveMenuItem);
	}

	@Override
	public void update(MainFrame frame) {
        boolean savePossible = frame.getImagePanel().getTexture() != null;
        saveMenuItem.setEnabled(savePossible);
	}

    public void showSavingPrompt(MainFrame frame) {
        int n = JOptionPane.showConfirmDialog(
                frame,
                "All unsaved changes of the current image will be lost.\nWould you like to save first?",
                "SAVEty first!",
                JOptionPane.YES_NO_OPTION);
        if(n == JOptionPane.YES_OPTION)
            save(frame);
    }

    public void saveAs(MainFrame frame) {
        File saveFile = showSaveDialog(frame);
        if (saveFile != null) {
            frame.setWorkingFile(saveFile);
            save(frame);
        }
    }
    
    public File showSaveDialog(MainFrame frame) {
        SaveImageFileDialog sd = new SaveImageFileDialog(frame, frame.getWorkingFile());
        return sd.getFile();
    }

    public void save(MainFrame frame) {
    	assert(frame.getWorkingFile() != null);
        Texture.saveAsPng(frame.getImagePanel().getTexture(), frame.getWorkingFile());
    }
}
