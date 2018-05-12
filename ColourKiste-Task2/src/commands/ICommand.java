package commands;
public interface ICommand<T>
{
    public void execute(T t);
    
    public void undo(T t);
}
