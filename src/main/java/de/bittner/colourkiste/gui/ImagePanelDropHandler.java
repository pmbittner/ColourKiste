package de.bittner.colourkiste.gui;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

public class ImagePanelDropHandler extends DropTargetAdapter
{
    private Workspace imagePanel;
    private DropTarget dropTarget;

    public ImagePanelDropHandler(Workspace imagePanel) {
        this.imagePanel = imagePanel;
        dropTarget = new DropTarget(this.imagePanel, this);
    }

    public void drop(DropTargetDropEvent evt) {
        int action = evt.getDropAction();
        evt.acceptDrop(action);
        try {
            Transferable data = evt.getTransferable();
            if (data.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                List<File> list = (List<File>)
                    data.getTransferData(DataFlavor.javaFileListFlavor);
                processFiles(list);
            }
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            evt.dropComplete(true);
            imagePanel.repaint();
        }
    }

    private void processFiles(List<File> files) {
        MainFrame frame = imagePanel.getMainFrame();
        for (File f : files) {
            try {
                frame.getCurrentWorkspaceTab().setWorkingFile(f);
            } catch(IllegalArgumentException iae) {
                JOptionPane.showMessageDialog(
                    frame,
                    "This file type is not supported!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
