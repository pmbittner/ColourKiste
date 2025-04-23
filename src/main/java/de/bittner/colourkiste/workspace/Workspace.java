package de.bittner.colourkiste.workspace;

import de.bittner.colourkiste.engine.Entity;
import de.bittner.colourkiste.engine.World;
import de.bittner.colourkiste.engine.components.graphics.TextureGraphics;
import de.bittner.colourkiste.engine.components.hitbox.TextureHitbox;
import de.bittner.colourkiste.event.EventHandler;
import de.bittner.colourkiste.gui.MainFrame;
import de.bittner.colourkiste.gui.io.ApplyTool;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.util.SizedStack;
import de.bittner.colourkiste.workspace.commands.resize.ResizeCommand;
import de.bittner.colourkiste.workspace.elements.resize.ResizeBar;
import de.bittner.colourkiste.workspace.elements.resize.Directions.Down;
import de.bittner.colourkiste.workspace.elements.resize.Directions.Left;
import de.bittner.colourkiste.workspace.elements.resize.Directions.Right;
import de.bittner.colourkiste.workspace.elements.resize.Directions.Top;
import de.bittner.colourkiste.workspace.remember.RememberFileSave;
import de.bittner.colourkiste.workspace.remember.RememberSomething;
import org.tinylog.Logger;
import org.variantsync.functjonal.Unit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Workspace
{
    private static final int UNDOABLE_ACTIONS_MAX = 50;

    /** GUI **/
    private final MainFrame frame;

    /** THE FILE WE ARE EDITING HERE **/
    private final TextureGraphics workpiece;
    private File workingFile = null;

    /** RESIZE BARS **/
    private final List<ResizeBar> resizeBars;
    private boolean resizersVisible = false;

    /** EDITING **/
    private final SizedStack<ICommand<Texture>> actionsDone;
    private final SizedStack<ICommand<Texture>> actionsUndone;

    private final World world;

    public final EventHandler<File> OnWorkingFileChanged = new EventHandler<>();
    public final EventHandler<Workspace> AfterEdit = new EventHandler<>();
    public final EventHandler<File> OnSave = new EventHandler<>();
    public final EventHandler<Unit> OnWorkpieceResized = new EventHandler<>();

    public Workspace(MainFrame frame) {
        this.frame = frame;

        actionsDone = new SizedStack<>(UNDOABLE_ACTIONS_MAX, cmd -> !(cmd instanceof RememberSomething));
        actionsUndone = new SizedStack<>(UNDOABLE_ACTIONS_MAX, cmd -> !(cmd instanceof RememberSomething));

        world = new World();

        workpiece = createNewWorkpiece();
        world.spawn(workpiece.getEntity());

        resizeBars = new ArrayList<>(4);
        for (final ResizeBar d : List.of(new Left(this), new Down(this), new Right(this), new Top(this))) {
            resizeBars.add(ResizeBar.create(d).require(ResizeBar.class));
        }

        this.OnWorkpieceResized.addListener(this::onResize);
        this.OnWorkingFileChanged.addListener(file -> getWorld().getCamera().reset());
    }

    private TextureGraphics createNewWorkpiece() {
        final Entity workpieceEntity = new Entity("Workpiece");
        workpieceEntity.setZ(World.BACKGROUND * 1000);

        final TextureGraphics workpiece = new TextureGraphics(null);
        workpieceEntity.add(workpiece);
        workpieceEntity.add(new TextureHitbox());
        workpieceEntity.add(new ApplyTool(this, frame.getToolBox()));

        return workpiece;
    }

    private void showResizers() {
        if (!resizersVisible) {
            resizeBars.forEach(r -> {
                world.spawn(r.getEntity());
                r.reset();
            });
        }

        resizersVisible = true;
    }

    private void hideResizers() {
        if (resizersVisible) {
            resizeBars.forEach(r -> world.despawn(r.getEntity()));
        }

        resizersVisible = false;
    }

    /** EDITING **/

    public void runCommand(ICommand<Texture> command) {
        if (getTexture() == null) {
            Logger.warn("Tried to run command {} on null workpiece! I am discarding it.", command);
            return;
        }

        if (command instanceof RememberSomething rs) {
            Logger.warn("Did not expect a remembrance command but got " + rs + " of type " + rs.getClass() + ". I am going to remember it but you should fix your code.");
            remember(rs);
            return;
        }

        if (command.execute(getTexture())) {
	        actionsDone.push(command);
	        actionsUndone.clear();
            AfterEdit.fire(this);
        }

        // TODO: Find a better way to determine when to do this
        if (command instanceof ResizeCommand) {
            OnWorkpieceResized.fire(Unit.Instance());
        }

        refreshAll();
    }

    public void remember(RememberSomething remembrance) {
        if (remembrance.policy() == RememberSomething.Policy.REMEMBER_LAST) {
            actionsDone.removeIf(r -> r.getClass().equals(remembrance.getClass()));
        }

        actionsDone.push(remembrance);

        if (remembrance instanceof RememberFileSave) {
            OnSave.fire(getWorkingFile());
        }
    }

    private void rememberFileSave() {
        remember(new RememberFileSave());
    }

    public void undo() {
        if (canUndo()) {
            final ICommand<Texture> undoCommand = actionsDone.pop();
            actionsUndone.push(undoCommand);

            if (undoCommand instanceof RememberSomething) {
                undo();
            } else {
                undoCommand.undo(workpiece.getTexture());
                // TODO: Find a better way to determine when to do this
                if (undoCommand instanceof ResizeCommand) {
                    OnWorkpieceResized.fire(Unit.Instance());
                }
                refreshAll();
            }

            if (!actionsDone.empty() && actionsDone.peek() instanceof RememberFileSave) {
                OnSave.fire(getWorkingFile());
            } else {
                AfterEdit.fire(this);
            }
        }
    }

    public void redo() {
        if (canRedo()) {
            // take the last undone command and mark it as done
            final ICommand<Texture> redoCommand = actionsUndone.pop();
            actionsDone.push(redoCommand);

            if (redoCommand instanceof RememberSomething) {
                // if it was just a remembrance, go ahead until we redo an actual edit
                redo();
            } else {
                // redo the command
                redoCommand.execute(workpiece.getTexture());
                // TODO: Find a better way to determine when to do this
                if (redoCommand instanceof ResizeCommand) {
                    OnWorkpieceResized.fire(Unit.Instance());
                }
                AfterEdit.fire(this);
                refreshAll();

                // Now remember all remembrances that came after the edit and before the subsequent (undone) edit
                while (!actionsUndone.empty() && actionsUndone.peek() instanceof RememberSomething) {
                    remember((RememberSomething) actionsUndone.pop());
                }
            }
        }
    }

    public boolean canUndo() {
        return !(actionsDone.empty() || actionsDone.stream().allMatch(cmd -> cmd instanceof RememberSomething));
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
    	workpiece.refreshRelativeTransform();
    }

    public void refreshAll() {
        frame.refresh();
    }
    
    /** GET AND SET **/

    private void setTexture(Texture texture){
        workpiece.setTexture(texture);
        
        if (texture == null) {
            world.despawn(workpiece.getEntity());
            hideResizers();
        } else {
        	if (!world.contains(workpiece.getEntity())) {
                world.spawn(workpiece.getEntity());
            }
            showResizers();
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

    // TODO: Fix this dirty hack.
    public int getComicifyValue() {
        return frame.getComicifyValue();
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
            if (actionsDone.empty() || !(actionsDone.peek() instanceof RememberFileSave)) {
                frame.getSaver().showSavingPrompt(this);
            }
        }
    }

    public void setWorkingFile(File file) {
        closeWorkingFile();
        setTexture(new Texture(file));
        // initially, the new files is saved
        rememberFileSave();
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

    public void save() throws IOException {
        final File dest = getWorkingFile();
        Texture.saveAsPng(getTexture(), dest);
        rememberFileSave();
        OnSave.fire(dest);
    }

    private void onResize(final Unit unit) {
        resizeBars.forEach(ResizeBar::reset);
    }
}
