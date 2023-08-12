package de.bittner.colourkiste.workspace.commands;

import de.bittner.colourkiste.workspace.ICommand;

public class IdCommand<T> implements ICommand<T> {
    @Override
    public final boolean execute(T t) {
        return false;
    }

    @Override
    public final void undo(T t) {}
}
