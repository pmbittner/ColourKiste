package de.bittner.colourkiste.gui;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

public class ImagePanelDropHandler extends DropTargetAdapter
{
    private final MainFrame frame;
    private final Component dropTargetComponent;

    public ImagePanelDropHandler(MainFrame frame, Component dropTarget) {
        this.frame = frame;
        this.dropTargetComponent = dropTarget;
        new DropTarget(dropTarget, this);
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
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        } finally {
            evt.dropComplete(true);
            dropTargetComponent.repaint();
        }
    }

    private void processFiles(final List<File> files) {
        final WorkspaceTab currentWorkSpaceTab = frame.getCurrentWorkspaceTab();
        for (final File f : files) {
            if (f == null) continue;
            try {
                if (currentWorkSpaceTab.getWorkspace().hasWorkingFile()) {
                    frame.setCurrentWorkspace(frame.createWorkspace(f));
                } else {
                    currentWorkSpaceTab.getWorkspace().setWorkingFile(f);
                }
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
