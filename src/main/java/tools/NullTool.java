package tools;
public class NullTool extends ToolAdapter
{
    public final static NullTool Instance = new NullTool();
    
    private NullTool() {
    	super("Null");
    }
}
