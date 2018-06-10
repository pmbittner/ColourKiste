package gui;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.io.OpenImageFileDialog;
import gui.io.SaveImageFileDialog;
import gui.menu.MenuBarItem;
import gui.menu.SaveMenuBarItem;
import rendering.Texture;
import tools.Tool;

public class MainFrame extends JFrame
{
	private JPanel toolPanel;
    private ImagePanel imagePanel;
    private File workingFile;
    private ImageSaver saver;
    
    private Map<String, JMenu> menusByName;
    private List<MenuBarItem> menuBarItems;
    private JMenuBar menuBar;

    public MainFrame(int resolutionWidth, int resolutionHeight, ImageSaver saver) {
        super("ColorKiller");
        
        this.saver = saver;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(resolutionWidth, resolutionHeight);
        setLocationRelativeTo(null);
        
        menuBar = new JMenuBar();
        add(menuBar);
        this.setJMenuBar(menuBar);
        
        menusByName = new HashMap<>();
        menuBarItems = new ArrayList<>();

        toolPanel = new JPanel(new GridBagLayout());
        
        imagePanel = new ImagePanel(this);
        addComponentListener(new ComponentListener() {
                public void componentHidden(ComponentEvent e) {}
                public void componentMoved(ComponentEvent e) {}
                public void componentShown(ComponentEvent e) {}
                public void componentResized(ComponentEvent e) {
                    imagePanel.onWindowResized();
                }
            });

        
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.weighty = 1.0;

        c.gridx = 0;
        c.fill = GridBagConstraints.VERTICAL;
        contentPane.add(toolPanel, c);
        
        c.gridx = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        contentPane.add(imagePanel, c);
    }
    
    public void addMenuBarItems(List<MenuBarItem> items) {
    	menuBarItems.addAll(items);
    	
    	for (MenuBarItem mbi : items)
    		mbi.create(this);
    }
    
    public void addTools(List<Tool> tools) {
    	if (!tools.isEmpty()) {
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.NORTHEAST;
            c.gridx = 0;
            c.weightx = 1;
            
            int row = 0;
    		for (Tool t : tools) {
    			c.gridy = row;
    			toolPanel.add(new ToolChooserButton(imagePanel, t), c);
    			++row;
    		}

    		// Empty space at bottom (a bit hacky)
            c.weighty = 1;
			c.gridy = row;
    		toolPanel.add(new JPanel(), c);
    	}
    }
    
    public void finalize() {
        updateGuiComponents();
        setVisible(true);
    }
    
    public JMenu getMenuWithName(String name) {
    	JMenu menu = menusByName.get(name);
    	if (menu == null) {
    		menu = new JMenu(name);
    		menuBar.add(menu);
    		menusByName.put(name, menu);
    	}
    	return menu;
    }
    
    public void setWorkingFile(File file) {
    	if (workingFile != null) {
    		saver.showSavingPrompt(this);
    	}
    	
        Texture t = new Texture(file);
        imagePanel.setTexture(t);
        workingFile = file;
    }

	public File getWorkingFile() {
		return workingFile;
	}

    public void updateGuiComponents() {
    	for (MenuBarItem mbi : menuBarItems)
    		mbi.update(this);
    }
    
    public ImagePanel getImagePanel() {
        return imagePanel;
    }
}
