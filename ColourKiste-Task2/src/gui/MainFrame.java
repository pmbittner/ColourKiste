package gui;
import javax.swing.*;

import properties.PropertyManager;
import rendering.Texture;

import java.io.File;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame
{
    private ImagePanel imagePanel;
    private File workingFile;

    private JMenuItem saveMenuItem, saveAsMenuItem, undoMenuItem, redoMenuItem;

    public MainFrame(int resolutionWidth, int resolutionHeight) {
        super("ColorKiller");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(resolutionWidth, resolutionHeight);
        setLocationRelativeTo(null);

        // Create Workspace
        imagePanel = new ImagePanel(this);
        addComponentListener(new ComponentListener() {
                public void componentHidden(ComponentEvent e) {}

                public void componentMoved(ComponentEvent e) {}

                public void componentShown(ComponentEvent e) {}

                public void componentResized(ComponentEvent e) {
                    imagePanel.onWindowResized();
                }
            });

        add(imagePanel);

        // Create Menu
        JMenuItem menuItem;

        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");

        if (PropertyManager.getProperty("ImageLoadingWizard")) {
	        menuItem = new JMenuItem("Open");
	        menuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
	        menuItem.addActionListener(new java.awt.event.ActionListener() {
	                public void actionPerformed(java.awt.event.ActionEvent evt) {
	                    openFile();
	                }
	            });
	        menuFile.add(menuItem);
        }

        if (PropertyManager.getProperty("ImageSavingWizard")) {
	        saveMenuItem = new JMenuItem("Save");
	        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
	        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
	                public void actionPerformed(java.awt.event.ActionEvent evt) {
	                    save();
	                }
	            });
	        menuFile.add(saveMenuItem);
	
	        saveAsMenuItem = new JMenuItem("Save as");
	        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
	                public void actionPerformed(java.awt.event.ActionEvent evt) {
	                    saveAs();
	                }
	            });
	        menuFile.add(saveAsMenuItem);
        }

        JMenu menuEdit = new JMenu("Edit");

        undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    imagePanel.undo();
                }
            });
        menuEdit.add(undoMenuItem);

        redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    imagePanel.redo();
                }
            });
        menuEdit.add(redoMenuItem);

        menuBar.add(menuFile);
        menuBar.add(menuEdit);

        add(menuBar);
        this.setJMenuBar(menuBar);

        updateGuiComponents();
        setVisible(true);
    }

    private void setWorkingFile(File file) {
        workingFile = file;
    }

    public void openFile() {
        OpenImageFileDialog od = new OpenImageFileDialog(this, null);
        File choosenFile = od.getFile();
        if(choosenFile != null)
            load(choosenFile);
    }

    public void load(File file) {
        Texture t = new Texture(file);
        if (workingFile != null)
            showSavingPrompt();
        imagePanel.setTexture(t);
        setWorkingFile(file);
    }

    public void showSavingPrompt() {
        int n = JOptionPane.showConfirmDialog(
                this,
                "All unsaved changes of the current image will be lost.\nWould you like to save first?",
                "SAVEty first!",
                JOptionPane.YES_NO_OPTION);
        if(n == JOptionPane.YES_OPTION)
            save();
    }

    public File showSaveDialog() {
        SaveImageFileDialog sd = new SaveImageFileDialog(this, workingFile);
        return sd.getFile();
    }

    public void saveAs() {
        File saveFile = showSaveDialog();
        if (saveFile != null) {
            setWorkingFile(saveFile);
            save();
        }
    }

    public void save() {
        if (workingFile == null)
            saveAs();
        else
            Texture.saveAsPng(imagePanel.getTexture(), workingFile);
    }

    public void updateGuiComponents() {
        boolean savePossible = imagePanel.getTexture() != null;
        if (PropertyManager.getProperty("ImageSavingWizard")) {
	        saveMenuItem.setEnabled(savePossible);
	        saveAsMenuItem.setEnabled(savePossible);
        }

        undoMenuItem.setEnabled(imagePanel.canUndo());
        redoMenuItem.setEnabled(imagePanel.canRedo());
    }
    
    public ImagePanel getImagePanel() {
        return imagePanel;
    }
}
