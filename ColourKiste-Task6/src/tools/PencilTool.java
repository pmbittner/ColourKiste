package tools;
import java.awt.Color;

import commands.DrawImageCommand;
import commands.ICommand;
import gui.Workspace;
import rendering.Texture;
import rendering.TextureWorkingElement;

public class PencilTool extends ToolAdapter
{
    private Color color;
    private TextureWorkingElement myElement;
    private int lastX, lastY;

    public PencilTool(Color color) {
    	super("Pencil");
        this.color = color;
    }
    
    private void strokeAtEdge(Texture t, int xIn, int yIn, int xOut, int yOut) {
    	// Calculate Intersection of Texture bounding rectangle and line ((lastX, lastY), (x, y))
    	// use clamp for now for easier calculation
    	int sx = Math.max(Math.min(t.getWidth(), xOut), 0);
    	int sy = Math.max(Math.min(t.getHeight(), yOut), 0);
    	t.drawLine(xIn, yIn, sx, sy);
    }
    
    private void strokeTo(int x, int y) {
    	if (myElement != null) {
	        Texture t = myElement.getTexture();
	        boolean newIn = t.contains(x, y);
	        boolean oldIn = t.contains(lastX, lastY);
	        
	        if (newIn && oldIn) {
		        t.drawLine(lastX, lastY, x, y);
	        } else if (oldIn) {
	        	strokeAtEdge(t, lastX, lastY, x, y);
	        } else if (newIn) {
	        	strokeAtEdge(t, x, y, lastX, lastY);
	        }
	        
	        getImagePanel().update();
    	}
    	
        lastX = x;
        lastY = y;
    }

    public void startUsage(Texture workpiece, int x, int y) {
    	if (workpiece == null) return;
    	
        Texture t = new Texture(
                workpiece.getWidth(),
                workpiece.getHeight()
            );
        t.setColor(color);
        
        if (t.contains(x, y)) {
            t.setColorAt(x, y, color);
        }
        
        myElement = new TextureWorkingElement(t);
        myElement.setLocation(0, 0);
        getImagePanel().spawn(myElement);
        lastX = x;
        lastY = y;
    }

    public void updateUsage(Texture workpiece, int x, int y) {
    	strokeTo(x, y);
    }

    public ICommand<Texture> finishUsage(Texture workpiece, int x, int y) {
    	ICommand<Texture> ret = null;
    	strokeTo(x, y);
    	
    	if (myElement != null) {
	        ret = new DrawImageCommand(myElement.getTexture(), 0, 0);
    	}
    	
        abortUsage();
        return ret;
    }

    public void abortUsage() {
    	if (myElement != null) {
	        Workspace ip = getImagePanel();
	        ip.despawn(myElement);
	        ip.update();
	        myElement = null;
    	}
    }
}
