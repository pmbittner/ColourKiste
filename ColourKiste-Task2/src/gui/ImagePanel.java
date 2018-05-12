package gui;
import javax.swing.JPanel;

import commands.ICommand;
import properties.PropertyManager;
import rendering.Camera;
import rendering.Texture;
import rendering.WorkingElement;
import tools.NullTool;
import tools.PencilTool;
import tools.Tool;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.dnd.*;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ImagePanel extends JPanel
{
    /** GUI **/
    private MainFrame frame;
    private DropTargetAdapter dropTargetHandler;
    private InputHandler inputHandler;

    /** EDITING **/
    private Stack<ICommand<Texture>> actionsDone;
    private Stack<ICommand<Texture>> actionsUndone;

    private Tool currentTool;

    private WorkingElement mainImage;
    private List<WorkingElement> workingElements;

    /** GRAPHICS and RENDERING **/
    private Camera camera;
    private Texture background;

    private AffineTransform imageTransform;

    public ImagePanel(MainFrame frame) {
        this.frame = frame;

        if (PropertyManager.getProperty("ImageLoadingDragAndDrop")) {
            dropTargetHandler = new ImagePanelDropHandler(this);
        }
        
        inputHandler = new InputHandler(this);

        this.addMouseListener(inputHandler);
        this.addMouseMotionListener(inputHandler);
        this.addMouseWheelListener(inputHandler);

        actionsDone = new Stack<ICommand<Texture>>();
        actionsUndone = new Stack<ICommand<Texture>>();
        setTool(NullTool.Instance);

        mainImage = new WorkingElement(null);
        workingElements = new ArrayList<WorkingElement>();

        imageTransform = new AffineTransform();

        camera = new Camera();

        background = createBackground();
    }

    /** EDITING **/

    public void runCommand(ICommand<Texture> command) {
        command.execute(mainImage.getTexture());
        actionsDone.push(command);
        actionsUndone.clear();
        update();
    }

    public void undo() {
        if (canUndo()) {
            ICommand<Texture> undoCommand = actionsDone.pop();
            undoCommand.undo(mainImage.getTexture());
            actionsUndone.push(undoCommand);
            update();
        }
    }

    public void redo() {
        if (canRedo()) {
            ICommand<Texture> redoCommand = actionsUndone.pop();
            redoCommand.execute(mainImage.getTexture());
            actionsDone.push(redoCommand);
            update();
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
        update();
    }

    public void despawn(WorkingElement element) {
        workingElements.remove(element);
        update();
    }

    protected void paintComponent(Graphics gc) {
        super.paintComponent(gc);

        Graphics2D g2 = (Graphics2D) gc;

        // draw Background
        for (int x = 0; x < this.getWidth(); x += background.getWidth())
            for (int y = 0; y < this.getHeight(); y += background.getHeight())
                g2.drawImage(background.getAwtImage(), null, x, y);

        // draw main Texture
        if(mainImage.getTexture() != null) {
            updateTransform(mainImage);
            g2.drawImage(mainImage.getTexture().getAwtImage(), imageTransform, null);
        }

        // draw all WorkingElements
        for (WorkingElement we : workingElements) {
            updateTransform(we);
            g2.drawImage(we.getTexture().getAwtImage(), imageTransform, null);
        }
    }

    public void update() {
        frame.updateGuiComponents();
        repaint();
    }

    private void updateTransform(WorkingElement element) {
        Texture texture = element.getTexture();
        if (texture == null) return;
        
        double
        zoom = camera.getZoom(),
        xpos = element.getX() - texture.getWidth() / 2,
        ypos = element.getY() - texture.getHeight() / 2;
        
        imageTransform.setTransform(
            zoom, 0,
            0, zoom,
            (zoom * xpos + camera.getX()) + getWidth() / 2,
            (zoom * ypos + camera.getY()) + getHeight() / 2);
    }
    
    private void updateTransform() {
        double zoom = camera.getZoom();
        
        imageTransform.setTransform(
            zoom, 0,
            0, zoom,
            camera.getX() + getWidth() / 2,
            camera.getY() + getHeight() / 2);
    }

    /** GET AND SET **/

    public void setTexture(Texture texture){
        mainImage.setTexture(texture);
        actionsDone.clear();
        actionsUndone.clear();

        // HARDCODED FOR NOW
        if (texture == null)
            setTool(NullTool.Instance);
        else
            setTool(new PencilTool(new Color(255, 0, 0)));

        camera.setLocation(0, 0);
        camera.setZoom(1);
            
        update();
    }

    public Texture getTexture(){
        return mainImage.getTexture();
    }

    public MainFrame getMainFrame() {
        return frame;
    }
    
    public void setTool(Tool tool) {
    	if (currentTool != null)
    		currentTool.setImagePanel(null);
    	
        currentTool = tool;
        
        currentTool.setImagePanel(this);
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public Camera getCamera() {
        return camera;
    }

    private Texture createBackground() {
        int size = 100;
        int step = 10;

        Texture background = new Texture(size, size);

        background.setColor(new java.awt.Color(100, 100, 100));
        background.fill();

        background.setColor(new java.awt.Color(120, 120, 120));
        for (int x = 0; x < size; x += step)
            for (int y = x % (2*step); y < size; y += 2*step)
                background.fillRect(x, y, step, step);

        return background;
    }

    ///////////// MATH /////////////////

    public boolean isPointOnImage(double x, double y) {
        Texture texture = mainImage.getTexture();
        if (texture == null) return false;
        return x >= 0 && x < texture.getWidth() && y >= 0 && y < texture.getHeight();
    }

    public Point2D screenToTextureCoord(double x, double y) {
        return screenToTextureCoord((int)x, (int)y);
    }

    public Point2D screenToTextureCoord(int x, int y) {
        try {
            updateTransform(mainImage);
            return imageTransform.inverseTransform(
                new Point(x, y),
                null);
        } catch (java.awt.geom.NoninvertibleTransformException e) {System.out.println(e);}
        return null;
    }
    
    public Point2D screenToLocalCoord(int x, int y) {
        try {
            updateTransform();
            return imageTransform.inverseTransform(
                new Point(x, y),
                null);
        } catch (java.awt.geom.NoninvertibleTransformException e) {System.out.println(e);}
        return null;
    }

    /** EVENTS **/

    public void onWindowResized() {
        setBounds(0, 0, frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
        repaint();
    }
}
