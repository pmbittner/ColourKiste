package gui;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;
import java.awt.datatransfer.*;
import javax.activation.MimetypesFileTypeMap;
import javax.swing.JOptionPane;

public class ImagePanelDropHandler extends DropTargetAdapter
{
    private ImagePanel imagePanel;
    private DropTarget dropTarget;

    public ImagePanelDropHandler(ImagePanel imagePanel) {
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
                frame.load(f);
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
