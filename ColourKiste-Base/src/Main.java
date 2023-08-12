import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import commands.Comicify;
import commands.UndoableTextureManipulation;
import gui.MainFrame;
import gui.menu.LoadMenuBarItem;
import gui.menu.MenuBarItem;
import gui.menu.RedoMenuBarItem;
import gui.menu.SaveMenuBarItem;
import gui.menu.UndoMenuBarItem;
import rendering.Vec4;
import rendering.kernels.Convolution;
import tools.*;

public abstract class Main
{
    public static MainFrame mainFrame;
    
    public static void main(String[] args) {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException e) {
            System.err.println("Error when setting look and feel");
        }

        Tool comicTool = new ClickTool("Comic", Comicify::new);
        List<Tool> tools = List.of(
                new Pipette(),
                new DotTool(),
                new PencilTool(1),
                new PencilTool(12),
                new FillTool(),
                new ColorSwitchTool(),
                new AreaSelectionTool(),
                comicTool,
                new ClickTool(
                        "Smoothen",
                        () -> UndoableTextureManipulation.Convert(t -> Convolution.smoothing().apply(t))
                        ));
        
        List<MenuBarItem> menu = new ArrayList<>();
        SaveMenuBarItem save = new SaveMenuBarItem();
        menu.add(new LoadMenuBarItem());
        menu.add(save);
        menu.add(new UndoMenuBarItem());
        menu.add(new RedoMenuBarItem());
        
        mainFrame = new MainFrame(800, 600, save);
        mainFrame.addTools(tools);
        mainFrame.addMenuBarItems(menu);
        mainFrame.finalizeInitialization();

        mainFrame.getToolBox().setTool(comicTool);
    }
}
