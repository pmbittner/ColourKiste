package de.bittner.colourkiste.gui.menu;

import javax.swing.*;

import de.bittner.colourkiste.gui.ActionMap;
import de.bittner.colourkiste.gui.MainFrame;
import org.tinylog.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

public class UndoMenuBarItem implements MenuBarItem {
	
	private JMenuItem undoMenuItem;
	
	@Override
	public void create(MainFrame frame) {
		undoMenuItem = new JMenuItem("Undo");
		frame.registerAccelerator(undoMenuItem, frame.getKeyMap().getFirstKeybindingForAction(ActionMap.UNDO));
		undoMenuItem.addActionListener(evt -> frame.getActionMap().runAction(ActionMap.UNDO));
        frame.getMenuWithName("Edit").add(undoMenuItem);
	}

	@Override
	public void update(MainFrame frame) {
		boolean enabled = false;
		if (frame.getCurrentWorkspaceTab() != null) {
			enabled = frame.getCurrentWorkspaceTab().getWorkspace().canUndo();
		}
		undoMenuItem.setEnabled(enabled);
	}
}
