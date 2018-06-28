import java.io.File;

aspect ImageLoadingArgs {
    after(String[] args) : execution(public static void main(String[])) && args(args) {
    	if (args.length == 1) {
    		Main.mainFrame.getCurrentWorkspaceTab().setWorkingFile(new File(args[0]));
		}
    }
}