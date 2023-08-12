package de.bittner.colourkiste.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.bittner.colourkiste.rendering.Texture;

public class WorkspaceTab extends JPanel {
	private File workingFile;
	private JTabbedPane tabbedPane;
	private Workspace imagePanel;
    private MainFrame frame;
    
    public WorkspaceTab(MainFrame frame, JTabbedPane tabbedPane) {
    	this.frame = frame;
    	this.tabbedPane = tabbedPane;
    	
    	imagePanel = new Workspace(frame);
        frame.addComponentListener(new ComponentListener() {
                public void componentHidden(ComponentEvent e) {}
                public void componentMoved(ComponentEvent e) {}
                public void componentShown(ComponentEvent e) {}
                public void componentResized(ComponentEvent e) {
                    imagePanel.onWindowResized();
                }
            });
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        add(imagePanel, c);
    }
    
    public void setWorkingFile(File file) {
    	if (workingFile != null) {
    		frame.getSaver().showSavingPrompt(this);
    	}
    	
        Texture t = new Texture(file);
        imagePanel.setTexture(t);
        workingFile = file;
        
        reassignWorkingFile(workingFile);
    }
    
    public void reassignWorkingFile(File file) {
        workingFile = file;

        for (int i = 0; i < tabbedPane.getTabCount(); ++i) {
        	if (tabbedPane.getComponentAt(i) == this) {
        		tabbedPane.setTitleAt(i, workingFile.getName());
        		break;
        	}
        }
        
        frame.updateGuiComponents();
    }

	public File getWorkingFile() {
		return workingFile;
	}
	
	public Workspace getImagePanel() {
		return imagePanel;
	}
	
	public MainFrame getFrame() {
		return frame;
	}
}
