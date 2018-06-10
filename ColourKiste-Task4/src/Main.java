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
    public static MainFrame mainFrame;
    
    public static void main(String[] args) {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {}
	    catch (ClassNotFoundException e) {}
	    catch (InstantiationException e) {}
	    catch (IllegalAccessException e) {}
    	
    	
        List<Tool> tools = new ArrayList<>();
        tools.add(new DotTool(Color.BLACK));
        tools.add(new PencilTool(Color.BLACK));
        tools.add(new FillTool(Color.ORANGE));
        tools.add(new ColorSwitchTool());
        tools.add(new AreaSelectionTool());
        
        List<MenuBarItem> menu = new ArrayList<>();
        SaveMenuBarItem save = new SaveMenuBarItem();
        menu.add(new LoadMenuBarItem());
        menu.add(save);
        menu.add(new UndoMenuBarItem());
        menu.add(new RedoMenuBarItem());
        
        mainFrame = new MainFrame(800, 600, save);
        mainFrame.addTools(tools);
        mainFrame.addMenuBarItems(menu);
        mainFrame.finalize();
    }
}
