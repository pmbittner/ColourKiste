package de.bittner.colourkiste.workspace;

import de.bittner.colourkiste.engine.*;
import de.bittner.colourkiste.engine.components.graphics.EntityGraphics;
import de.bittner.colourkiste.math.Transform;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Texture;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class WorkspaceScreen extends JPanel implements Screen {
    private final Workspace workspace;

    private final AffineTransform viewTransform;

    /** GRAPHICS and RENDERING **/
    private final Texture background;

    public WorkspaceScreen(Workspace workspace) {
        super(true);
        this.workspace = workspace;
        this.background = createBackground();
        this.viewTransform = new AffineTransform();
    }

    private static Texture createBackground() {
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

    private void updateViewTransform() {
        final Camera c = getWorkspace().getWorld().getCamera();
        final double zoom = c.getZoom();
        final Vec2 camPos = c.getLocation();

        viewTransform.setTransform(
                zoom, 0,
                0, zoom,
                camPos.x() + getWidth() / 2.0,
                camPos.y() + getHeight() / 2.0);
    }

    protected void paintComponent(Graphics gc) {
        super.paintComponent(gc);

        final World w = workspace.getWorld();
        updateViewTransform();

        final Graphics2D screen = (Graphics2D) gc;

        // draw Background
        for (int x = 0; x < this.getWidth(); x += background.getWidth()) {
            for (int y = 0; y < this.getHeight(); y += background.getHeight()) {
                screen.drawImage(background.getAwtImage(), null, x, y);
            }
        }

        // draw all WorkingElements
        final RenderTarget renderTarget = new RenderTarget(screen, getCamera());
        renderTarget.pushTransform(viewTransform);
        for (final Entity e : w.getEntities()) {
            final EntityGraphics eGraphics = e.get(EntityGraphics.class);
            if (eGraphics != null) {
                eGraphics.render(renderTarget);
            }
        }
        renderTarget.popTransform();
    }

    @Override
    public void refresh() {
        repaint();
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public AffineTransform getViewTransform() {
        return viewTransform;
    }

    public Vec2 screenToTextureCoord(int x, int y) {
        return screenToTextureCoord((double) x, y);
    }

    public Vec2 screenToTextureCoord(double x, double y) {
        try {
            return Vec2.from(
                    Transform.mult(
                            viewTransform,
                            workspace.getWorkpiece().getEntity().getRelativeTransform(),
                            workspace.getWorkpiece().getRelativeTransform()
                    ).inverseTransform(
                            new Point2D.Double(x, y),
                            null)
            );
        } catch (java.awt.geom.NoninvertibleTransformException e) {
            Logger.error(e);
        }
        return null;
    }

    public Vec2 textureToScreenCoord(int x, int y) {
        return Vec2.from(Transform.mult(viewTransform, workspace.getWorkpiece().getEntity().getRelativeTransform()).transform(new Point(x, y), null));
    }

    @Override
    public Vec2 screenToViewportCoord(final Vec2 vec2) {
        try {
            updateViewTransform();
            return Vec2.from(
                    viewTransform.inverseTransform(vec2.toPoint2D(), null)
            );
        } catch (java.awt.geom.NoninvertibleTransformException e) {
            Logger.error(e);
        }
        return null;
    }

    @Override
    public Camera getCamera() {
        return workspace.getWorld().getCamera();
    }

    @Override
    public void addInputListener(InputListener inputListener) {
        Screen.super.addInputListener(inputListener);
        addKeyListener(inputListener);
        addMouseListener(inputListener);
        addMouseMotionListener(inputListener);
        addMouseWheelListener(inputListener);
    }

    @Override
    public void removeInputListener(InputListener inputListener) {
        removeKeyListener(inputListener);
        removeMouseListener(inputListener);
        removeMouseMotionListener(inputListener);
        removeMouseWheelListener(inputListener);
        Screen.super.removeInputListener(inputListener);
    }
}
