package de.bittner.colourkiste;

import de.bittner.colourkiste.imageprocessing.kernels.Erosion;
import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.workspace.ICommand;
import de.bittner.colourkiste.workspace.Workspace;
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
                new PencilTool(5),
                new PencilTool(20),
                new PencilTool(30),
                new PencilTool(50),
                new FillTool(),
                new ColorSwitchTool(),
                new AreaSelectionTool(),
                new ToolAdapter("Eroder") {
                    @Override
                    public ICommand<Texture> use(Texture workpiece, int x, int y) {
                        return new ICommand<Texture>() {
                            private Texture prev;
                            @Override
                            public boolean execute(Texture texture) {
                                prev = texture;
                                texture = new Texture(prev);
                                texture.convolution(Erosion.Erosion());
                                texture.convolution(Erosion.Erosion());
                                texture.convolution(Erosion.Erosion());
                                texture.convolution(Erosion.Dilatation());
                                texture.convolution(Erosion.Dilatation());
                                texture.convolution(Erosion.Dilatation());
                                return true;
                            }

                            @Override
                            public void undo(Texture texture) {
                                texture.setAwtImage(prev.getAwtImage());
                            }
                        };
                    }
                },
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

//        Logger.info("Keybindings loaded");
//        mainFrame.getKeyMap().getAllBindings().forEach((k, a) -> System.out.println(k + " = " + a));

        mainFrame.addTools(tools);
        mainFrame.addMenuBarItems(menu);
        mainFrame.finalizeInitialization();

        mainFrame.getToolBox().setTool(comicTool);
    }
}
