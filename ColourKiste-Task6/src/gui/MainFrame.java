package gui;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.menu.MenuBarItem;
import tools.Tool;
import tools.ToolBox;

public class MainFrame extends JFrame
{
    private ImageSaver saver;
    private ToolBox tools;
    
	private JPanel toolPanel;
	private JTabbedPane tabbedPane;
	private WorkspaceTab currentWorkspaceTab;
    
    private Map<String, JMenu> menusByName;
    private List<MenuBarItem> menuBarItems;
    private JMenuBar menuBar;

    private boolean newTabClickLock;
    
    public MainFrame(int resolutionWidth, int resolutionHeight, ImageSaver saver) {
        super("ColorKiller");
        
        this.saver = saver;
        tools = new ToolBox();

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
        
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	if (newTabClickLock) return;
            	
            	if (tabbedPane.getSelectedComponent() instanceof WorkspaceTab) {
                    WorkspaceTab wt = (WorkspaceTab) tabbedPane.getSelectedComponent();
                    if (wt != null)
                    	setCurrentWorkspaceTab(wt);
            	}
                
                JTabbedPane tabbedPane = (JTabbedPane)e.getSource();
                if(tabbedPane.getSelectedIndex() == tabbedPane.indexOfTab(" + "))
                {
                	newTabClickLock = true;
                	WorkspaceTab wt = createWorkspace("new");
                    tabbedPane.setSelectedComponent(wt);
                    newTabClickLock = false;
                }
                
                updateGuiComponents();
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
        contentPane.add(tabbedPane, c);

        tabbedPane.addTab(" + ", null, new JPanel(), null);
        //createWorkspace("new"); // This is done implicitly, as addChangeListener is invoked once the tabbedPane is added.
        setCurrentWorkspaceTab((WorkspaceTab) tabbedPane.getSelectedComponent());
    }
    
    public WorkspaceTab createWorkspace(String title) {
    	WorkspaceTab wt = new WorkspaceTab(this, tabbedPane);
        tabbedPane.insertTab(title, null, wt, "", tabbedPane.getTabCount() - 1);
        return wt;
    }

	private void setCurrentWorkspaceTab(WorkspaceTab wt) {
		this.currentWorkspaceTab = wt;
		tools.setCurrentWorkspace(wt.getImagePanel());
        updateGuiComponents();
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
    			toolPanel.add(new ToolChooserButton(this.tools, t), c);
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

    public void updateGuiComponents() {
    	for (MenuBarItem mbi : menuBarItems)
    		mbi.update(this);
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
}
