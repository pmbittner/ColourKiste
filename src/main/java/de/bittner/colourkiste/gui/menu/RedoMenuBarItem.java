package de.bittner.colourkiste.gui.menu;


import de.bittner.colourkiste.ActionMap;
import de.bittner.colourkiste.gui.MainFrame;

import javax.swing.*;


public class RedoMenuBarItem implements MenuBarItem {

	private JMenuItem redoMenuItem;
	
	@Override
	public void create(MainFrame frame) {
        redoMenuItem = new JMenuItem("Redo");
		frame.registerAccelerator(redoMenuItem, frame.getKeyMap().getFirstKeybindingForAction(ActionMap.REDO));
        redoMenuItem.addActionListener(evt -> frame.getActionMap().runAction(ActionMap.REDO, evt));
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
