package de.bittner.colourkiste.tools;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import de.bittner.colourkiste.commands.CutRectangleCommand;
import de.bittner.colourkiste.commands.ICommand;
import de.bittner.colourkiste.gui.Workspace;
import de.bittner.colourkiste.rendering.RectangleWorkingElement;
import de.bittner.colourkiste.rendering.Texture;

public class AreaSelectionTool extends ToolAdapter {

	RectangleWorkingElement selectionArea;
	int startX, startY;
	
	public AreaSelectionTool() {
		super("Cut");
		selectionArea = new RectangleWorkingElement(Color.CYAN);
	}
	
	public void startUsage(Texture workpiece, int x, int y) {
		if (workpiece == null) return;
		
		selectionArea.setLocation(x - workpiece.getWidth()/2.0, y - workpiece.getHeight()/2.0);
		selectionArea.setSize(0, 0);
        getWorkspace().spawn(selectionArea);
        
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
	    	rect.setRect(rect.getX() + (workpiece.getWidth()/2.0), rect.getY() + (workpiece.getHeight()/2.0), rect.getWidth()+1, rect.getHeight()+1);
	    	Rectangle2D.Double textureBounds = new Rectangle2D.Double(0, 0, workpiece.getWidth()+1, workpiece.getHeight()+1);
	    	Rectangle2D.intersect(rect, textureBounds, textureBounds);	    	
	        command = new CutRectangleCommand(textureBounds);
    	}

        abortUsage();
    	return command;
    }

    public void abortUsage() {
        Workspace ip = getWorkspace();
        ip.despawn(selectionArea);
        ip.refreshAll();
    }
}
