package gui.io;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Paul
 */
public abstract class ImageFileChooserFrame {
    private boolean selectionValid;
    
    protected JFileChooser jFileChooser;

    /**
     * Creates new form FileFrame
     */
    public ImageFileChooserFrame(JFrame parentFrame, File defaultDirectory) {
        jFileChooser = new JFileChooser();
        
        jFileChooser.setFileFilter(
            new FileNameExtensionFilter("Image Files", javax.imageio.ImageIO.getReaderFileSuffixes())
        );
        
        if (defaultDirectory != null)
            jFileChooser.setCurrentDirectory(defaultDirectory);

        int result = showDialog(parentFrame);
        selectionValid = result == jFileChooser.APPROVE_OPTION;
    }
    
    protected abstract int showDialog(JFrame parentFrame);

    public File getFile(){
        if(selectionValid)
            return jFileChooser.getSelectedFile();
        else
            return null;
    }   
}