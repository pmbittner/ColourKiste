package de.bittner.colourkiste.gui;

import de.bittner.colourkiste.gui.menu.MenuBarItem;
import de.bittner.colourkiste.workspace.tools.Tool;
import de.bittner.colourkiste.workspace.tools.ToolBox;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame
{
    private static final String MAKE_NEW_TAB_TITLE = " + ";

    private final ImageSaver saver;
    private final ToolBox tools;
    
	private final JPanel toolPanel;
	private final JTabbedPane tabbedPane;
    private final JColorChooser colorChooser;
	private WorkspaceTab currentWorkspaceTab;
    
    private final Map<String, JMenu> menusByName;
    private final List<MenuBarItem> menuBarItems;
    private final JMenuBar menuBar;

    private boolean newTabClickLock;
    
    public MainFrame(int resolutionWidth, int resolutionHeight, ImageSaver saver) {
        super("ColorKiller");
        
        //// DOMAIN-SPECIFIC INITIALIZATION
        
        this.saver = saver;
        tools = new ToolBox();
        
        /// FRAME SETUP

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(resolutionWidth, resolutionHeight);
        setLocationRelativeTo(null);
        
        //// MENU BAR
        
        menuBar = new JMenuBar();
        add(menuBar);
        this.setJMenuBar(menuBar);
        
        menusByName = new HashMap<>();
        menuBarItems = new ArrayList<>();

        //// MAIN GUI COMPONENTS
        
        toolPanel = new JPanel(new GridBagLayout());
        toolPanel.setBackground(Color.LIGHT_GRAY);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(e -> {
            if (newTabClickLock) return;

            final JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
            if(tabbedPane.getSelectedIndex() == tabbedPane.indexOfTab(MAKE_NEW_TAB_TITLE))
            {
                newTabClickLock = true;
                WorkspaceTab wt = createWorkspace();
                tabbedPane.setSelectedComponent(wt);
                setCurrentWorkspace(wt);
                newTabClickLock = false;
            } else if (tabbedPane.getSelectedComponent() instanceof WorkspaceTab wt) {
                setCurrentWorkspace(wt);
            }
        });
        
        colorChooser = new JColorChooser(Color.BLACK);
        colorChooser.setPreviewPanel(new JPanel());
        
        //// ADDING ELEMENTS TO UI
        
        final Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());

        final JPanel topRow = new JPanel(new GridBagLayout());
        topRow.setBackground(Color.LIGHT_GRAY);
        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.weighty = 1;
            c.weightx = 1;
            c.fill = GridBagConstraints.BOTH;
            contentPane.add(topRow, c);
        }
        
        final JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new FlowLayout(FlowLayout.LEFT));
        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 1;
            c.weighty = 0;
            c.weightx = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            contentPane.add(bottomRow, c);
        }
        
        // top row
        {
            // left de.bittner.colourkiste.tools, right image
            GridBagConstraints c = new GridBagConstraints();
            c.gridy = 0;
            c.weighty = 1.0;

            c.gridx = 0;
            c.fill = GridBagConstraints.VERTICAL;
            topRow.add(toolPanel, c);

            c.gridx = 1;
            c.weightx = 1.0;
            c.fill = GridBagConstraints.BOTH;
            topRow.add(tabbedPane, c);
        }

        // bottom row
        {
//            GridBagConstraints nstraints.VERTICAL;
//            c.anchor = GridBagConstraints.NORTHEAST;
//
//            c.gridx = 0;
//            c.weightx = 0;
//            bottomRow.add(colorCc = new GridBagConstraints();
//            c.fill = GridBagCohooser, c);
            bottomRow.add(colorChooser);
        }
        
        //// FINAL SETUP

        tabbedPane.addTab(MAKE_NEW_TAB_TITLE, null, new JPanel(), null);
        // This is done implicitly, as addChangeListener is invoked once the tabbedPane is added.
        //    createWorkspace("new");
        //    setCurrentWorkspace((WorkspaceTab) tabbedPane.getSelectedComponent());
    }

    public WorkspaceTab createWorkspace(final File file) {
        final WorkspaceTab wt = new WorkspaceTab(this, tabbedPane);
        wt.getWorkspace().setWorkingFile(file);
        addTab(wt);
        return wt;
    }
    
    public WorkspaceTab createWorkspace() {
        WorkspaceTab wt = new WorkspaceTab(this, tabbedPane);
        addTab(wt);
        return wt;
    }

    private void addTab(final WorkspaceTab wt) {
        final int newTabIndex = tabbedPane.getTabCount() - 1;
        wt.addToTabPaneAt(newTabIndex);
//        tabbedPane.insertTab(title, null, wt, "", newTabIndex);
    }

	public void setCurrentWorkspace(WorkspaceTab wt) {
		this.currentWorkspaceTab = wt;
		tools.setCurrentWorkspace(wt.getWorkspace());
        refreshGuiComponents();
	}
    
    public void addMenuBarItems(List<MenuBarItem> items) {
    	menuBarItems.addAll(items);
    	
    	for (MenuBarItem mbi : items)
    		mbi.create(this);
    }
    
    public void addTools(List<Tool> tools) {
    	if (!tools.isEmpty()) {
            toolPanel.removeAll();
            
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.NORTHEAST;
            c.gridx = 0;
            c.weightx = 1;
            
            int row = 0;
        	ToolChooserButton firstTool = null;
    		for (Tool t : tools) {
    			c.gridy = row;
    			ToolChooserButton toolButton = new ToolChooserButton(this.tools, t);
    			if (firstTool == null) {
    				firstTool = toolButton;
    			}
    			toolPanel.add(toolButton, c);
    			++row;
    		}
            assert firstTool != null;
            firstTool.setSelected(true);

    		// Empty space at bottom (a bit hacky)
            c.weighty = 1;
			c.gridy = row;
    		toolPanel.add(new JPanel(), c);
    	}
    }
    
    public void finalizeInitialization() {
        refreshGuiComponents();
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

    public void refresh() {
        refreshWorkspace();
        refreshGuiComponents();
    }

    public void refreshWorkspace() {
        currentWorkspaceTab.refresh();
    }

    public void refreshGuiComponents() {
    	for (final MenuBarItem mbi : menuBarItems)
    		mbi.update(this);
    }

    public WorkspaceTab getWorkspace(int i) {
        return (WorkspaceTab) tabbedPane.getComponentAt(i);
    }

	public WorkspaceTab getCurrentWorkspaceTab() {
		return (WorkspaceTab) tabbedPane.getSelectedComponent();
	}

	public ImageSaver getSaver() {
		return saver;
	}

	public ToolBox getToolBox() {
		return tools;
	}
    
    public Color getColor() {
        return colorChooser.getColor();
    }
    
    public void setColor(final Color color) {
        colorChooser.setColor(color);
    }
}
