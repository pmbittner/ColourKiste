package de.bittner.colourkiste.gui.menu;

import javax.swing.JMenuItem;

import de.bittner.colourkiste.gui.MainFrame;

public class RedoMenuBarItem implements MenuBarItem {

	private JMenuItem redoMenuItem;
	
	@Override
	public void create(MainFrame frame) {
        redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoMenuItem.addActionListener(evt -> frame.getCurrentWorkspaceTab().getWorkspace().redo());
        frame.getMenuWithName("Edit").add(redoMenuItem);
	}

	@Override
	public void update(MainFrame frame) {
		boolean enabled = false;
		if (frame.getCurrentWorkspaceTab() != null) {
			enabled = frame.getCurrentWorkspaceTab().getWorkspace().canRedo();
		}
		redoMenuItem.setEnabled(enabled);
	}

}
