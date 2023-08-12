package de.bittner.colourkiste.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventHandler<T> {
    private final List<Consumer<T>> listeners;

    public EventHandler() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(final Consumer<T> listener) {
        listeners.add(listener);
    }

    public void removeListener(final Consumer<T> listener) {
        listeners.remove(listener);
    }

    public void fire(final T info) {
        for (final Consumer<T> listener : listeners) {
            listener.accept(info);
        }
    }
}
