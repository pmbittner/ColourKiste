package gui;
import javax.swing.JFrame;

import java.io.File;

public class SaveImageFileDialog extends ImageFileChooserFrame
{
    public SaveImageFileDialog(JFrame parentFrame, File defaultDirectory) {
        super(parentFrame, defaultDirectory);
    }
    
    protected int showDialog(JFrame parentFrame) {
        return jFileChooser.showSaveDialog(parentFrame);
    }
}
