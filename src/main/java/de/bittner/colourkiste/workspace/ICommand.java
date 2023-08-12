package de.bittner.colourkiste.workspace;

public interface ICommand<T>
{
    boolean execute(T t);
    void undo(T t);
}
