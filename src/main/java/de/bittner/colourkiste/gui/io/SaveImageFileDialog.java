package de.bittner.colourkiste.gui.io;
import java.io.File;

import javax.swing.JFrame;

public class SaveImageFileDialog extends ImageFileChooserFrame
{
    public SaveImageFileDialog(JFrame parentFrame, File defaultDirectory) {
        super(parentFrame, defaultDirectory);
    }
    
    protected int showDialog(JFrame parentFrame) {
        return jFileChooser.showSaveDialog(parentFrame);
    }
}
