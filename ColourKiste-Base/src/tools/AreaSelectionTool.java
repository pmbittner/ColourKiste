package tools;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import commands.CutRectangleCommand;
import commands.ICommand;
import gui.Workspace;
import rendering.RectangleWorkingElement;
import rendering.Texture;

public class AreaSelectionTool extends ToolAdapter {

	RectangleWorkingElement selectionArea;
	int startX, startY;
	
	public AreaSelectionTool() {
		super("Cut");
		selectionArea = new RectangleWorkingElement(Color.CYAN);
	}
	
	public void startUsage(Texture workpiece, int x, int y) {
		if (workpiece == null) return;
		
		selectionArea.setLocation(x - workpiece.getWidth()/2, y - workpiece.getHeight()/2);
		selectionArea.setSize(0, 0);
        getImagePanel().spawn(selectionArea);
        
        startX = x;
        startY = y;
    }

    public void updateUsage(Texture workpiece, int x, int y) {
    	selectionArea.setSize(x - startX, y - startY);
    }

    public ICommand<Texture> finishUsage(Texture workpiece, int x, int y) {
    	ICommand<Texture> command = null;
    	
    	if (workpiece != null) {
	    	Rectangle2D.Double rect = selectionArea.getRect();
	    	rect.setRect(rect.getX() + (workpiece.getWidth()/2), rect.getY() + (workpiece.getHeight()/2), rect.getWidth()+1, rect.getHeight()+1);
	    	Rectangle2D.Double textureBounds = new Rectangle2D.Double(0, 0, workpiece.getWidth()+1, workpiece.getHeight()+1);
	    	Rectangle2D.intersect(rect, textureBounds, textureBounds);	    	
	        command = new CutRectangleCommand(textureBounds);
    	}

        abortUsage();
    	return command;
    }

    public void abortUsage() {
        Workspace ip = getImagePanel();
        ip.despawn(selectionArea);
        ip.update();
    }
}
