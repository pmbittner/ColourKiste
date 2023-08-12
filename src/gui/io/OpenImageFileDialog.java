package gui.io;
import java.io.File;

import javax.swing.JFrame;

public class OpenImageFileDialog extends ImageFileChooserFrame
{
    public OpenImageFileDialog(JFrame parentFrame, File defaultDirectory) {
        super(parentFrame, defaultDirectory);
    }
    
    protected int showDialog(JFrame parentFrame) {
        return jFileChooser.showOpenDialog(parentFrame);
    }
}
