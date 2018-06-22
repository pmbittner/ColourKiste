import java.awt.Color;
import java.io.File;
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
    public static void main(String[] args) {
    	original(args);
    	if (args.length == 1) {
        	mainFrame.setWorkingFile(new File(args[0]));
		}
    }
}
