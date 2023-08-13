package de.bittner.colourkiste;

import de.bittner.colourkiste.workspace.commands.Comicify;
import de.bittner.colourkiste.workspace.commands.UndoableTextureManipulation;
import de.bittner.colourkiste.gui.MainFrame;
import de.bittner.colourkiste.gui.menu.*;
import de.bittner.colourkiste.imageprocessing.kernels.Convolution;
import de.bittner.colourkiste.workspace.tools.*;
import org.tinylog.Logger;

import javax.swing.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class Main
{
    public static Path DEFAULT_KEYBINDINGS_FILE = Path.of("res", "keymap", "default.csv");
    public static Path VIM_KEYBINDINGS_FILE = Path.of("res", "keymap", "vim.csv");

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

        mainFrame.getKeyMap().bindAll(KeyMap.fromFile(DEFAULT_KEYBINDINGS_FILE, KeyMap.CSV_DELIMITER_DEFAULT), true);
        mainFrame.getKeyMap().bindAll(KeyMap.fromFile(VIM_KEYBINDINGS_FILE, KeyMap.CSV_DELIMITER_DEFAULT), false);

        Logger.info("Keybindings loaded");
        mainFrame.getKeyMap().getAllBindings().forEach((k, a) -> System.out.println(k + " = " + a));

        mainFrame.addTools(tools);
        mainFrame.addMenuBarItems(menu);
        mainFrame.finalizeInitialization();

        mainFrame.getToolBox().setTool(comicTool);
    }
}
