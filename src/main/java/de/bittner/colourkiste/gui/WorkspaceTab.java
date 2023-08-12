package de.bittner.colourkiste.gui;

import de.bittner.colourkiste.rendering.WorkspaceScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class WorkspaceTab extends JPanel {
    private final MainFrame frame;
    private final JTabbedPane tabbedPane;

    private final Workspace workspace;
    private final WorkspaceScreen screen;
    
    public WorkspaceTab(MainFrame frame, final JTabbedPane tabbedPane) {
    	this.frame = frame;
        this.tabbedPane = tabbedPane;

        this.workspace = new Workspace(
                frame,
                () -> getWorkspaceScreen().getWidth(),
                () -> getWorkspaceScreen().getHeight()
        );
        this.screen = new WorkspaceScreen(workspace);

        setupWorkspace();
        setupWorkspaceScreen();
    }

    private void setupWorkspace() {
        workspace.OnWorkingFileChanged.addListener(file -> {
            for (int i = 0; i < tabbedPane.getTabCount(); ++i) {
                if (tabbedPane.getComponentAt(i) == this) {
                    tabbedPane.setTitleAt(i, file.getName());
                    break;
                }
            }
        });
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

        InputHandler inputHandler = new InputHandler(workspace, frame.getToolBox());
        screen.addMouseListener(inputHandler);
        screen.addMouseMotionListener(inputHandler);
        screen.addMouseWheelListener(inputHandler);

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

    public void refresh() {
        workspace.refresh();
        screen.refresh();
    }
}
