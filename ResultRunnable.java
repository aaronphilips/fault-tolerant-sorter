// small generic class that return the specified type from a runnable object;
public interface ResultRunnable<T> extends Runnable{
  public T getResult();
}
