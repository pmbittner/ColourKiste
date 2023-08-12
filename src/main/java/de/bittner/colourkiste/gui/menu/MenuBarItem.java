package de.bittner.colourkiste.gui.menu;

import de.bittner.colourkiste.gui.MainFrame;

public interface MenuBarItem {
	void create(MainFrame frame);
	
	void update(MainFrame frame);
}
