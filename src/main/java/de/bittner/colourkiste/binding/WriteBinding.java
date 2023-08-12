package de.bittner.colourkiste.binding;

@FunctionalInterface
public interface WriteBinding<T> {
   void set(T val);
}
