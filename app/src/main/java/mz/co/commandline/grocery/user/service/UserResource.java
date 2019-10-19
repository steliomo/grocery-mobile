package mz.co.commandline.grocery.user.service;

import mz.co.commandline.grocery.user.dto.UserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserResource {

    @POST("users/login")
    Call<UserDTO> login(@Body UserDTO userDTO);
}
