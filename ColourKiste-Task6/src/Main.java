import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.MainFrame;
import gui.menu.MenuBarItem;
import gui.menu.RedoMenuBarItem;
import gui.menu.SaveMenuBarItem;
import gui.menu.UndoMenuBarItem;
import tools.Tool;

public abstract class Main
{
    public static MainFrame mainFrame;
    static List<MenuBarItem> menu;
    
    private static void createTools(List<Tool> tools) {}
    
    public static void main(String[] args) {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {}
	    catch (ClassNotFoundException e) {}
	    catch (InstantiationException e) {}
	    catch (IllegalAccessException e) {}

        List<Tool> tools = new ArrayList<>();
    	createTools(tools);
        
        menu = new ArrayList<MenuBarItem>();
        SaveMenuBarItem save = new SaveMenuBarItem();
        menu.add(save);
        menu.add(new UndoMenuBarItem());
        menu.add(new RedoMenuBarItem());
        
        mainFrame = new MainFrame(800, 600, save);
        mainFrame.addTools(tools);
        mainFrame.addMenuBarItems(menu);
        mainFrame.finalize();
    }
}
