package de.bittner.colourkiste.gui.menu;

import java.io.File;

import javax.swing.JMenuItem;

import de.bittner.colourkiste.gui.ActionMap;
import de.bittner.colourkiste.gui.MainFrame;
import de.bittner.colourkiste.gui.WorkspaceTab;
import de.bittner.colourkiste.gui.io.OpenImageFileDialog;

public class LoadMenuBarItem implements MenuBarItem {

	@Override
	public void create(MainFrame frame) {
		JMenuItem openMenuItem = new JMenuItem("Open");
		frame.registerAccelerator(openMenuItem, frame.getKeyMap().getFirstKeybindingForAction(ActionMap.OPEN));
        openMenuItem.addActionListener(evt -> frame.getActionMap().runAction(ActionMap.OPEN));
        frame.getMenuWithName("File").add(openMenuItem);
	}
	

	@Override
	public void update(MainFrame frame) {
		
	}
}
