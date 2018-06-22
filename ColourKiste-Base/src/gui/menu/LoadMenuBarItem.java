package gui.menu;

import java.io.File;

import javax.swing.JMenuItem;

import gui.MainFrame;
import gui.WorkspaceTab;
import gui.io.OpenImageFileDialog;

public class LoadMenuBarItem implements MenuBarItem {

	@Override
	public void create(MainFrame frame) {
		JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	OpenImageFileDialog od = new OpenImageFileDialog(frame, null);
                    File choosenFile = od.getFile();
                    if(choosenFile != null) {
                    	WorkspaceTab t = frame.createWorkspace(choosenFile.getName());
                    	t.setWorkingFile(choosenFile);
                    }
                }
            });
        frame.getMenuWithName("File").add(openMenuItem);
	}
	

	@Override
	public void update(MainFrame frame) {
		
	}
}
