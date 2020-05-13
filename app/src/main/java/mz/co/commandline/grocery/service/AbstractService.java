package mz.co.commandline.grocery.service;

import java.io.IOException;

import mz.co.commandline.grocery.dto.GenericDTO;
import mz.co.commandline.grocery.listner.ResponseListner;
import retrofit2.Response;

public abstract class AbstractService<T extends GenericDTO> {

    public void setBodyError(Response<T> response, ResponseListner<T> listner) {
        try {
            listner.error(response.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
