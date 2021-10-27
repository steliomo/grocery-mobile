package mz.co.commandline.grocery.generics.service;

import com.google.gson.Gson;

import java.io.IOException;

import mz.co.commandline.grocery.generics.dto.ErrorMessage;
import mz.co.commandline.grocery.generics.dto.GenericDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import retrofit2.Response;

public abstract class AbstractService<T extends GenericDTO> {

    public void setBodyError(Response<T> response, ResponseListner<T> listner) {
        try {
            Gson gson = new Gson();
            ErrorMessage errorMessage = gson.fromJson(response.errorBody().string(), ErrorMessage.class);
            listner.businessError(errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract <M> M getResource();
}
