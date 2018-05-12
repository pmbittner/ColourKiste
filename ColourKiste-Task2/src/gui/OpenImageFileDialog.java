package gui;
import javax.swing.JFrame;

import java.io.File;

public class OpenImageFileDialog extends ImageFileChooserFrame
{
    public OpenImageFileDialog(JFrame parentFrame, File defaultDirectory) {
        super(parentFrame, defaultDirectory);
    }
    
    protected int showDialog(JFrame parentFrame) {
        return jFileChooser.showOpenDialog(parentFrame);
    }
}
