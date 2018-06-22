package commands;
public interface ICommand<T>
{
    public boolean execute(T t);
    
    public void undo(T t);
}
