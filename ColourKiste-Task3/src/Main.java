import java.io.File;

import gui.MainFrame;

public abstract class Main
{
    public static MainFrame mainFrame;
    
    public static void main(String[] args) {
    	mainFrame = new MainFrame(500, 300);
    	
    	// #if ImageLoadingArgs
    		if (args.length == 1) {
            	mainFrame.load(new File(args[0]));
    		}
    	// #endif
    }
}
