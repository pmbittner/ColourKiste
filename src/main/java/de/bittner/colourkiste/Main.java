package de.bittner.colourkiste;

import de.bittner.colourkiste.commands.Comicify;
import de.bittner.colourkiste.commands.UndoableTextureManipulation;
import de.bittner.colourkiste.gui.MainFrame;
import de.bittner.colourkiste.gui.menu.*;
import de.bittner.colourkiste.rendering.kernels.Convolution;
import de.bittner.colourkiste.tools.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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
