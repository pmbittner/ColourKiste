package de.bittner.colourkiste.workspace.elements.resize;

import de.bittner.colourkiste.engine.Camera;
import de.bittner.colourkiste.engine.Entity;
import de.bittner.colourkiste.engine.EntityComponent;
import de.bittner.colourkiste.engine.components.hitbox.DynamicRectangleHitbox;
import de.bittner.colourkiste.engine.components.hitbox.RectangleHitbox;
import de.bittner.colourkiste.engine.components.hitbox.StaticRectangleHitbox;
import de.bittner.colourkiste.engine.components.input.EntityDragNDrop;
import de.bittner.colourkiste.math.Degrees;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.math.geometry.Box;
import de.bittner.colourkiste.workspace.Workspace;
import de.bittner.colourkiste.workspace.commands.resize.ResizeCommand;

public abstract class ResizeBar extends EntityComponent implements EntityDragNDrop.DragPosition {
    public static Vec2 DEFAULT_BAR_SIZE = new Vec2(Camera.zoomMax, Camera.zoomMax * 2);
    private final Degrees rotation;
    private final Workspace workspace;
    private Vec2 dragStartPos;
    private boolean pressed;

    protected ResizeBar(double rotationDegrees, final Workspace workspace) {
        this.rotation = new Degrees(rotationDegrees);
        this.workspace = workspace;
    }

    void OnDragStart() {
        dragStartPos = getEntity().getLocation();
        pressed = true;
    }

    void OnDragEnd() {
        final Vec2 dragEndPos = getEntity().getLocation();
        this.workspace.runCommand(resize(workspace.getWorld().getCamera(), dragEndPos.minus(dragStartPos)));
        pressed = false;
    }

    public abstract ResizeCommand resize(final Camera camera, final Vec2 delta);

    public abstract void reset();

    public Workspace getWorkspace() {
        return workspace;
    }

    protected double getHalfWorkpieceWidth() {
        return workspace.getTexture().getWidth() / 2.0;
    }

    protected double getHalfWorkpieceHeight() {
        return workspace.getTexture().getHeight() / 2.0;
    }

    protected abstract int getBarLength();

    public boolean isPressed() {
        return pressed;
    }

    public static Entity create(final ResizeBar resizer) {
        final Entity resizeBar = new Entity("ResizeBar-" + resizer.getClass().getSimpleName());
        resizeBar.setRotation(resizer.rotation);

        final EntityDragNDrop dragNDrop = new EntityDragNDrop(resizer);
        dragNDrop.OnDragStart.addListener(u -> resizer.OnDragStart());
        dragNDrop.OnDragEnd.addListener(u -> resizer.OnDragEnd());
        resizeBar.add(resizer);
        resizeBar.add(dragNDrop);

        final double barWidth = Camera.zoomMax;
//        resizeBar.add(new StaticRectangleHitbox(new Box(barWidth, 2 * barWidth).translate(new Vec2(barWidth / 2.0, 0))));
//        resizeBar.add(new StaticRectangleHitbox(new Box(barWidth, 15).translate(new Vec2(barWidth / 2.0, 0))));
        resizeBar.add(new DynamicRectangleHitbox(() ->
                new Box(barWidth, resizer.getBarLength())
                        .translate(new Vec2(barWidth / 2.0, 0.0))
        ));
        resizeBar.add(new ResizeBarGraphics());

        return resizeBar;
    }
}