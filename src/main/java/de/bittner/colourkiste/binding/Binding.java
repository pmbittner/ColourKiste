package de.bittner.colourkiste.binding;

public class Binding<T> implements ReadBinding<T>, WriteBinding<T> {
    private final ReadBinding<T> getter;
    private final WriteBinding<T> setter;

    public Binding(final ReadBinding<T> getter, final WriteBinding<T> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public T get() {
        return getter.get();
    }

    @Override
    public void set(T val) {
        setter.set(val);
    }
}
