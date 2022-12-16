package commands;

public interface ICommand<T>
{
    boolean execute(T t);
    void undo(T t);
}
