package de.bittner.colourkiste.gui.menu;

import javax.swing.JMenuItem;

import de.bittner.colourkiste.gui.MainFrame;

public class UndoMenuBarItem implements MenuBarItem {
	
	private JMenuItem undoMenuItem;
	
	@Override
	public void create(MainFrame frame) {
		undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	frame.getCurrentWorkspaceTab().getImagePanel().undo();
                }
            });
        frame.getMenuWithName("Edit").add(undoMenuItem);
	}

	@Override
	public void update(MainFrame frame) {
		boolean enabled = false;
		if (frame.getCurrentWorkspaceTab() != null) {
			enabled = frame.getCurrentWorkspaceTab().getImagePanel().canUndo();
		}
		undoMenuItem.setEnabled(enabled);
	}
}
