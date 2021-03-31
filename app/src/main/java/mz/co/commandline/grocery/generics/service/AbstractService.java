package mz.co.commandline.grocery.generics.service;

import java.io.IOException;

import mz.co.commandline.grocery.generics.dto.GenericDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import retrofit2.Response;

public abstract class AbstractService<T extends GenericDTO> {

    public void setBodyError(Response<T> response, ResponseListner<T> listner) {
        try {
            listner.error(response.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract <M> M getResource();
}
