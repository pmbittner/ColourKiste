package de.bittner.colourkiste.gui;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import de.bittner.colourkiste.binding.ReadBinding;
import de.bittner.colourkiste.commands.ICommand;
import de.bittner.colourkiste.event.EventHandler;
import de.bittner.colourkiste.rendering.*;

public class Workspace
{
    /** GUI **/
    private final MainFrame frame;

    /** THE FILE WE ARE EDITING HERE **/
    private final TextureWorkingElement mainImage;
    private File workingFile = null;

    /** EDITING **/
    private final Stack<ICommand<Texture>> actionsDone;
    private final Stack<ICommand<Texture>> actionsUndone;

    private final List<WorkingElement> workingElements;

    private final Camera camera;
    AffineTransform viewTransform;

    private final ReadBinding<Integer> width, height;

    public final EventHandler<File> OnWorkingFileChanged = new EventHandler<>();

    public Workspace(MainFrame frame, ReadBinding<Integer> width, ReadBinding<Integer> height) {
        this.frame = frame;
        this.width = width;
        this.height = height;

        actionsDone = new Stack<>();
        actionsUndone = new Stack<>();

        mainImage = new TextureWorkingElement(null);
        workingElements = new ArrayList<>();

        camera = new Camera();
        viewTransform = new AffineTransform();
    }

    /** EDITING **/

    public void runCommand(ICommand<Texture> command) {
        if (command.execute(mainImage.getTexture())) {
	        actionsDone.push(command);
	        actionsUndone.clear();
        }
        refreshAll();
    }

    public void undo() {
        if (canUndo()) {
            ICommand<Texture> undoCommand = actionsDone.pop();
            undoCommand.undo(mainImage.getTexture());
            actionsUndone.push(undoCommand);
            refreshAll();
        }
    }

    public void redo() {
        if (canRedo()) {
            ICommand<Texture> redoCommand = actionsUndone.pop();
            redoCommand.execute(mainImage.getTexture());
            actionsDone.push(redoCommand);
            refreshAll();
        }
    }

    public boolean canUndo() {
        return !actionsDone.empty();
    }

    public boolean canRedo() {
        return !actionsUndone.empty();
    }

    /** RENDERING **/

    public void spawn(WorkingElement element) {
        workingElements.add(element);
        refreshAll();
    }

    public void despawn(WorkingElement element) {
        workingElements.remove(element);
        refreshAll();
    }

    public void refresh() {
    	mainImage.updateTransform();
    }

    public void refreshAll() {
        frame.refresh();
    }
    
    public void updateViewTransform() {
        double zoom = camera.getZoom();
        
        viewTransform.setTransform(
            zoom, 0,
            0, zoom,
            camera.getX() + width.get() / 2.0,
            camera.getY() + height.get() / 2.0);
    }
    
    /** GET AND SET **/

    public void setTexture(Texture texture){
        mainImage.setTexture(texture);
        
        if (texture == null) {
            workingElements.remove(mainImage);
        } else {
        	if (!workingElements.contains(mainImage))
        		workingElements.add(0, mainImage);
        }
        
        actionsDone.clear();
        actionsUndone.clear();

        camera.setLocation(0, 0);
        camera.setZoom(1);

        refreshAll();
    }

    public Texture getTexture(){
        return mainImage.getTexture();
    }

    public MainFrame getMainFrame() {
        return frame;
    }

    public Camera getCamera() {
        return camera;
    }

    ///////////// MATH /////////////////

    public boolean isPointOnImage(double x, double y) {
        Texture texture = mainImage.getTexture();
        if (texture == null) return false;
        return x >= 0 && x < texture.getWidth() && y >= 0 && y < texture.getHeight();
    }

    public Point2D screenToTextureCoord(int x, int y) {
        return screenToTextureCoord((double)x, (double)y);
    }

    public Point2D screenToTextureCoord(double x, double y) {
        try {
            return mainImage.getAbsoluteTransform(viewTransform).inverseTransform(
                new Point2D.Double(x, y),
                null);
        } catch (java.awt.geom.NoninvertibleTransformException e) {System.out.println(e);}
        return null;
    }
    
    public Point2D textureToScreenCoord(int x, int y) {
        return mainImage.getAbsoluteTransform(viewTransform).transform(new Point(x, y), null);
    }
    
    public Point2D screenToLocalCoord(int x, int y) {
        return screenToLocalCoord((double)x, (double)y);
    }
    
    public Point2D screenToLocalCoord(double x, double y) {
        try {
            updateViewTransform();
            return viewTransform.inverseTransform(
            		new Point2D.Double(x, y),
                null);
        } catch (java.awt.geom.NoninvertibleTransformException e) {System.out.println(e);}
        return null;
    }

    public List<WorkingElement> getWorkingElements() {
        return workingElements;
    }

    public AffineTransform getViewTransform() {
        return viewTransform;
    }

    public void closeWorkingFile() {
        if (workingFile != null) {
            frame.getSaver().showSavingPrompt(this);
        }
    }

    public void setWorkingFile(File file) {
        closeWorkingFile();
        setTexture(new Texture(file));
        reassignWorkingFile(file);
    }

    public void reassignWorkingFile(File file) {
        workingFile = file;
        OnWorkingFileChanged.fire(workingFile);
        frame.refreshGuiComponents();
    }

    public File getWorkingFile() {
        return workingFile;
    }

    public boolean hasWorkingFile() {
        return workingFile != null;
    }
}
