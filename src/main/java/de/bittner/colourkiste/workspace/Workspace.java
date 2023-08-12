package de.bittner.colourkiste.workspace;

import de.bittner.colourkiste.engine.Entity;
import de.bittner.colourkiste.engine.World;
import de.bittner.colourkiste.engine.components.TextureGraphics;
import de.bittner.colourkiste.event.EventHandler;
import de.bittner.colourkiste.gui.MainFrame;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.workspace.commands.ICommand;

import java.io.File;
import java.util.Stack;

public class Workspace
{
    /** GUI **/
    private final MainFrame frame;

    /** THE FILE WE ARE EDITING HERE **/
    private final TextureGraphics workpiece;
    private File workingFile = null;

    /** EDITING **/
    private final Stack<ICommand<Texture>> actionsDone;
    private final Stack<ICommand<Texture>> actionsUndone;

    private final World world;

    public final EventHandler<File> OnWorkingFileChanged = new EventHandler<>();

    public Workspace(MainFrame frame) {
        this.frame = frame;

        actionsDone = new Stack<>();
        actionsUndone = new Stack<>();

        world = new World();

        workpiece = createNewWorkpiece();
        world.spawn(workpiece.getEntity());
    }

    private static TextureGraphics createNewWorkpiece() {
        final Entity workpieceEntity = new Entity();
        workpieceEntity.setZ(World.BACKGROUND * 1000);

        final TextureGraphics workpiece = new TextureGraphics(null);
        workpieceEntity.add(workpiece);

        return workpiece;
    }

    /** EDITING **/

    public void runCommand(ICommand<Texture> command) {
        if (command.execute(workpiece.getTexture())) {
	        actionsDone.push(command);
	        actionsUndone.clear();
        }
        refreshAll();
    }

    public void undo() {
        if (canUndo()) {
            ICommand<Texture> undoCommand = actionsDone.pop();
            undoCommand.undo(workpiece.getTexture());
            actionsUndone.push(undoCommand);
            refreshAll();
        }
    }

    public void redo() {
        if (canRedo()) {
            ICommand<Texture> redoCommand = actionsUndone.pop();
            redoCommand.execute(workpiece.getTexture());
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

    public World getWorld() {
        return world;
    }

    public void refresh() {
        // TODO: Do we need this?
    	workpiece.updateTransform();
    }

    public void refreshAll() {
        frame.refresh();
    }
    
    /** GET AND SET **/

    private void setTexture(Texture texture){
        workpiece.setTexture(texture);
        
        if (texture == null) {
            world.despawn(workpiece.getEntity());
        } else {
        	if (!world.contains(workpiece.getEntity())) {
                world.spawn(workpiece.getEntity());
            }
        }
        
        actionsDone.clear();
        actionsUndone.clear();

        refreshAll();
    }

    public Texture getTexture(){
        return workpiece.getTexture();
    }

    public MainFrame getMainFrame() {
        return frame;
    }

    public TextureGraphics getWorkpiece() {
        return workpiece;
    }

    ///////////// MATH /////////////////

    public boolean isPointOnImage(double x, double y) {
        Texture texture = workpiece.getTexture();
        if (texture == null) return false;
        return x >= 0 && x < texture.getWidth() && y >= 0 && y < texture.getHeight();
    }

    public boolean isPointOnImage(Vec2 p) {
        return isPointOnImage(p.x(), p.y());
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
