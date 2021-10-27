package mz.co.commandline.grocery.generics.listner;

import mz.co.commandline.grocery.generics.dto.ErrorMessage;

public abstract class ResponseListner<T> {

    public abstract void success(T response);

    public abstract void error(String message);

    public void businessError(ErrorMessage errorMessage) {
        throw new RuntimeException("No response implemented...");
    }
}
