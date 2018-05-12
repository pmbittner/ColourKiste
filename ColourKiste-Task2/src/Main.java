import java.io.File;

import gui.MainFrame;
import properties.PropertyManager;

public abstract class Main
{
    public static MainFrame mainFrame;
    
    public static void main(String[] args) {
    	mainFrame = new MainFrame(500, 300);
    	
    	if (PropertyManager.getProperty("ImageLoadingArgs")) {
    		if (args.length == 1) {
            	mainFrame.load(new File(args[0]));
    		}
    	}
    }
}
