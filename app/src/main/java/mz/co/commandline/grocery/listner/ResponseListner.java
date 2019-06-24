package mz.co.commandline.grocery.listner;

public interface ResponseListner<T> {
    void success(T response);

    void error(String message);
}
