package de.bittner.colourkiste.gui;

import de.bittner.colourkiste.binding.Property;
import de.bittner.colourkiste.engine.input.CameraDragAndDrop;
import de.bittner.colourkiste.engine.input.EntityInputManager;
import de.bittner.colourkiste.engine.input.ZoomViaMouseWheel;
import de.bittner.colourkiste.workspace.Workspace;
import de.bittner.colourkiste.workspace.WorkspaceScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

public class WorkspaceTab extends JPanel {
    private static final String TITLE_FOR_TABS_WITHOUT_FILE = "unnamed";

    private final MainFrame frame;
    private final JTabbedPane tabbedPane;
    final ClosableTabComponent tabHeader;

    private final Workspace workspace;
    private final WorkspaceScreen screen;

    public final Property<String> title = new Property<>(TITLE_FOR_TABS_WITHOUT_FILE);
    
    public WorkspaceTab(MainFrame frame, final JTabbedPane tabbedPane) {
    	this.frame = frame;
        this.tabbedPane = tabbedPane;
        this.tabHeader = new ClosableTabComponent(tabbedPane);

        this.workspace = new Workspace(frame);
        this.screen = new WorkspaceScreen(workspace);

        setupWorkspace();
        setupWorkspaceScreen();
    }

    private void setupWorkspace() {
        // Event listeners that update the tab header name to
        // "<filename>" in the image was saved or freshly laoded
        // "*<filename>" in case the image was edited
        workspace.OnWorkingFileChanged.addListener(file -> title.set(file.getName()));
        workspace.OnSave.addListener(file -> {
            if (file == null) {
                title.set(TITLE_FOR_TABS_WITHOUT_FILE);
            } else {
                title.set(file.getName());
            }
        });
        workspace.AfterEdit.addListener(wp -> title.set("*" + wp.getWorkingFile().getName()));

//        final Entity debug = new Entity("Green Debug Square");
//        debug.setZ(World.FOREGROUND * 10);
//        final Box debugBox = new Box(
//                new Vec2(-50, -50),
//                new Vec2(50, 50)
//        );
//        debug.add(new RectangleHitbox(debugBox));
//        debug.add(new FilledRectangleGraphics(Color.GREEN));
//        debug.add(new OnClickComponent((entity, button, pos) -> {
//            if (button == MouseEvent.BUTTON1) {
//                entity.setRotation(entity.getRotation().toDegrees().add(10));
//                screen.refresh();
//            }
//            return true;
//        }, screen));
//        debug.add(new EntityDragNDrop());

//        workspace.getWorld().spawn(debug);

//        final Entity rbr = ResizeBar.createRight(workspace);
//        rbr.setZ(World.FOREGROUND * 100);
//        rbr.setLocation(rbr.getLocation().withX(debugBox.getWidth() / 2.0));
//        workspace.getWorld().spawn(rbr);
    }

    private void setupWorkspaceScreen() {
        frame.addComponentListener(new ComponentListener() {
            public void componentHidden(ComponentEvent e) {}
            public void componentMoved(ComponentEvent e) {}
            public void componentShown(ComponentEvent e) {}
            public void componentResized(ComponentEvent e) {
                screen.repaint();
            }
        });

        new ImagePanelDropHandler(frame, screen);

        screen.addInputListener(new CameraDragAndDrop(MouseEvent.BUTTON3));
        screen.addInputListener(new ZoomViaMouseWheel());
        screen.addInputListener(new EntityInputManager(screen, workspace.getWorld()));

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        add(screen, c);
    }

    public Workspace getWorkspace() {
        return workspace;
    }
	
	public WorkspaceScreen getWorkspaceScreen() {
		return screen;
	}

    public int getTabIndex() {
        return tabbedPane.indexOfTabComponent(tabHeader);
    }

    public void refresh() {
        workspace.refresh();
        screen.refresh();
    }

    public void addToTabPaneAt(int newTabIndex) {
        tabbedPane.insertTab(title.get(), null, this, "", newTabIndex);
        tabHeader.OnClose.addListener(u -> workspace.closeWorkingFile());
        tabbedPane.setTabComponentAt(newTabIndex, tabHeader);
        title.OnPropertyChanged.addListener(newTitle -> tabbedPane.setTitleAt(getTabIndex(), newTitle));
    }
}
