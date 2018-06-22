package gui.menu;

import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import gui.ImageSaver;
import gui.MainFrame;
import gui.io.SaveImageFileDialog;
import rendering.Texture;

public class SaveMenuBarItem {
	
	JMenuItem saveAsMenuItem;
	
	@Override
	public void create(MainFrame frame) {
		original(frame);

		JMenu fileMenu = frame.getMenuWithName("File");
		
        saveAsMenuItem = new JMenuItem("Save as");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    saveAs(frame);
                }
            });
        fileMenu.add(saveAsMenuItem);
	}

	@Override
	public void update(MainFrame frame) {
        original(frame);
        boolean savePossible = frame.getImagePanel().getTexture() != null;
        saveAsMenuItem.setEnabled(savePossible);
	}

    public void save(MainFrame frame) {
        if (frame.getWorkingFile() == null)
            saveAs(frame);
        else
            original(frame);
    }
}
