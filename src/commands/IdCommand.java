package commands;

public class IdCommand<T> implements ICommand<T> {
    @Override
    public boolean execute(T t) {
        return false;
    }

    @Override
    public void undo(T t) {

    }
}
