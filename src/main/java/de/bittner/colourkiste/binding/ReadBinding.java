package de.bittner.colourkiste.binding;

@FunctionalInterface
public interface ReadBinding<T> {
    T get();
}
