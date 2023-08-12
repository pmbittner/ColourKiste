package de.bittner.colourkiste.workspace.commands;

public interface ICommand<T>
{
    boolean execute(T t);
    void undo(T t);
}
