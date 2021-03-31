package mz.co.commandline.grocery.generics.listner;

public interface ResponseListner<T> {
    void success(T response);

    void error(String message);
}
