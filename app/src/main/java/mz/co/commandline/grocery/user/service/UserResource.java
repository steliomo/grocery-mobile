package mz.co.commandline.grocery.user.service;

import mz.co.commandline.grocery.generics.dto.EnumsDTO;
import mz.co.commandline.grocery.user.dto.UnitDetail;
import mz.co.commandline.grocery.user.dto.UserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserResource {

    @POST("users/login")
    Call<UserDTO> login(@Body UserDTO userDTO);

    @PUT("users/reset-password")
    Call<UserDTO> resetPassword(@Body UserDTO userDTO);

    @POST("users/signup")
    Call<UserDTO> signUp(@Body UserDTO userDTO);

    @POST("grocery-users/add-saler")
    Call<UserDTO> addSaler(@Body UserDTO userDTO);

    @GET("groceries/unit-types")
    Call<EnumsDTO> getUnitTypes();

    @GET("grocery-users/unit-detail/{unitUuid}")
    Call<UnitDetail> getUnitDetail(@Path("unitUuid") String unitUuid);
}
