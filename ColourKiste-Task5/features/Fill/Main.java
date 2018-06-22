import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.MainFrame;
import gui.menu.LoadMenuBarItem;
import gui.menu.MenuBarItem;
import gui.menu.RedoMenuBarItem;
import gui.menu.SaveMenuBarItem;
import gui.menu.UndoMenuBarItem;
import tools.*;

public abstract class Main
{
	private static void createTools() {
    	original();
        tools.add(new FillTool(Color.ORANGE));
    }
}
