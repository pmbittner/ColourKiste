package de.bittner.colourkiste.engine;

import de.bittner.colourkiste.event.EventHandler;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class World {
    public static final double BACKGROUND = -1;
    public static final double FOREGROUND = +1;

    private final List<Entity> entities;

    public EventHandler<Entity> OnEntitySpawned = new EventHandler<>();
    public EventHandler<Entity> OnEntityDespawned = new EventHandler<>();

    public World() {
        entities = new ArrayList<>();
    }

    /** RENDERING **/

    public void spawn(Entity entity) {
        entities.add(entity);
        entity.setWorld(this);
        sortEntities();
        OnEntitySpawned.fire(entity);
    }

    public void despawn(Entity entity) {
        entities.remove(entity);
        entity.setWorld(null);
        OnEntityDespawned.fire(entity);
    }

    ///////////// MATH /////////////////

    public List<Entity> getEntities() {
        return entities;
    }

    public boolean contains(Entity entity) {
        return entities.contains(entity);
    }

    private void sortEntities() {
        entities.sort(Comparator.comparingDouble(Entity::getZ));
    }
}
