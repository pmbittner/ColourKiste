package de.bittner.colourkiste.gui.menu;

import java.io.File;

import javax.swing.JMenuItem;

import de.bittner.colourkiste.gui.MainFrame;
import de.bittner.colourkiste.gui.WorkspaceTab;
import de.bittner.colourkiste.gui.io.OpenImageFileDialog;

public class LoadMenuBarItem implements MenuBarItem {

	@Override
	public void create(MainFrame frame) {
		JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.addActionListener(evt -> {
			OpenImageFileDialog od = new OpenImageFileDialog(frame, null);
			File choosenFile = od.getFile();
			if(choosenFile != null) {
				WorkspaceTab t = frame.createWorkspace(choosenFile);
				t.getWorkspace().setWorkingFile(choosenFile);
			}
		});
        frame.getMenuWithName("File").add(openMenuItem);
	}
	

	@Override
	public void update(MainFrame frame) {
		
	}
}
