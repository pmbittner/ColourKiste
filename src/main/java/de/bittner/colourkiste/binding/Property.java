package de.bittner.colourkiste.binding;

import de.bittner.colourkiste.event.EventHandler;

public class Property<T> implements ReadBinding<T>, WriteBinding<T> {
    private T val;
    public final EventHandler<T> OnPropertyChanged = new EventHandler<>();

    public Property(final T val) {
        this.val = val;
    }

    @Override
    public T get() {
        return val;
    }

    @Override
    public void set(T val) {
        this.val = val;
        OnPropertyChanged.fire(this.val);
    }
}
