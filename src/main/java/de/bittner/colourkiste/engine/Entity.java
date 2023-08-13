package de.bittner.colourkiste.engine;

import de.bittner.colourkiste.math.Transform;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.util.Assert;
import org.variantsync.functjonal.Cast;

import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class Entity {
    private World world;
    private Vec2 location;
    private final String name;
    private double z;
    private final AffineTransform relativeTransform;
    private final Map<Class<?>, EntityComponent> components;

    public Entity(final String name) {
        this.name = name;
        z = 0;
        location = Vec2.all(0);
        relativeTransform = new AffineTransform();
        this.components = new HashMap<>();
    }

    public void add(final EntityComponent component) {
        Assert.assertNull(component.getEntity());

        Class<?> cls = component.getClass();
        do {
            final EntityComponent old = components.put(cls, component);
            if (old != null) {
                throw new RuntimeException("This entity has already a component of type " + cls.getSimpleName() + " registered!");
            }
            cls = cls.getSuperclass();
        } while (cls != null && !cls.equals(EntityComponent.class));

        component.setEntity(this);
    }

    public void remove(final EntityComponent component) {
        Assert.assertTrue(component.getEntity() == this);

        Class<?> cls = component.getClass();
        do {
            components.remove(cls, component);
            cls = cls.getSuperclass();
        } while (cls != null && !cls.equals(EntityComponent.class));

        component.setEntity(null);
    }

    public <T extends EntityComponent> T get(final Class<T> type) {
        return Cast.unchecked(components.get(type));
    }

    /**
     * Same as {@link #get(Class)} but never returns null.
     * In case this entity does not have the requested component, this method will crash.
     */
    public <T extends EntityComponent> T require(final Class<T> type) {
        final T comp = get(type);
        Assert.assertNotNull(comp);
        return comp;
    }

    /**
     * Runs the given consumer in case this entity has the desired component.
     */
    public <T extends EntityComponent> void forComponent(
            final Class<T> type,
            final Consumer<T> ifPresent
    ) {
        final T component = get(type);
        if (component != null) {
            ifPresent.accept(component);
        }
    }

    public AffineTransform getRelativeTransform() {
        return relativeTransform;
    }

    public void updateTransform() {
        relativeTransform.setTransform(
                1, 0,
                0, 1,
                location.x(), location.y());
    }

    public void setLocation(final Vec2 location) {
        Assert.assertNotNull(location);
        this.location = location;
        updateTransform();
    }

    public Vec2 getLocation() {
        return location;
    }

    public Vec2 toEntitySpace(final Vec2 worldPos) {
        updateTransform();
        return Transform.invert(getRelativeTransform(), worldPos);
    }

    /**
     * TODO: Document whether low z is top or bottom?
     * @param z
     */
    public void setZ(double z) {
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    void setWorld(final World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
