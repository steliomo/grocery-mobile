package mz.co.commandline.grocery.Listner;

public interface ResponseListner<T> {
    void success(T response);

    void error(String message);
}
