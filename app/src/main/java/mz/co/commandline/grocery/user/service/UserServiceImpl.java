package mz.co.commandline.grocery.user.service;

import android.util.Base64;

import java.io.IOException;

import javax.inject.Inject;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.infra.SharedPreferencesManager;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.service.RetrofitService;
import mz.co.commandline.grocery.user.dto.GroceryUserDTO;
import mz.co.commandline.grocery.user.dto.UserDTO;
import mz.co.commandline.grocery.user.dto.UserRole;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserServiceImpl implements UserService {

    private static final String TOKEN = "TOKEN";
    private static final String GROCERY = "GROCERY";
    private static final String GROCERY_USER = "GROCERY_USER";
    private static final String FULL_NAME = "FULL_NAME";

    @Inject
    SharedPreferencesManager preferencesManager;

    @Inject
    RetrofitService retrofitService;

    @Inject
    public UserServiceImpl() {
    }

    private UserResource getResource() {
        return retrofitService.getResource(UserResource.class);
    }

    @Override
    public String getToken() {
        return preferencesManager.getString(TOKEN);
    }

    @Override
    public void login(final String username, final String password, final ResponseListner<UserDTO> responseListner) {

        getResource().login(new UserDTO(username, password)).enqueue(new Callback<UserDTO>() {

            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()) {

                    UserDTO userDTO = response.body();
                    responseListner.success(userDTO);

                    String token = prepareToken(username, password);

                    preferencesManager.storeString(TOKEN, token);
                    preferencesManager.storeString(GROCERY, userDTO.getGroceryUserDTO().getGroceryDTO().toString());
                    preferencesManager.storeString(GROCERY_USER, userDTO.getGroceryUserDTO().toString());
                    preferencesManager.storeString(FULL_NAME, userDTO.getFullName());

                    return;
                }

                setErrorBody(response, responseListner);
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    private void setErrorBody(Response<UserDTO> response, ResponseListner<UserDTO> responseListner) {
        try {
            responseListner.error(response.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isLoggedIn() {

        String token = preferencesManager.getString(TOKEN);

        if (token != null) {
            return true;
        }

        return false;
    }

    @Override
    public GroceryDTO getGroceryDTO() {
        return getGroceryUser().getGroceryDTO();
    }

    @Override
    public GroceryUserDTO getGroceryUser() {

        String groceryString = preferencesManager.getString(GROCERY);
        String groceryUserString = preferencesManager.getString(GROCERY_USER);

        String[] split = groceryString.split("_");
        GroceryDTO groceryDTO = new GroceryDTO();
        groceryDTO.setId(Long.valueOf(split[0]));
        groceryDTO.setUuid(split[1]);

        GroceryUserDTO groceryUserDTO = new GroceryUserDTO();
        String[] groceryUserSplit = groceryUserString.split("_");
        groceryUserDTO.setGroceryDTO(groceryDTO);
        groceryUserDTO.setUserRole(UserRole.valueOf(groceryUserSplit[0]));
        groceryUserDTO.setExpiryDate(groceryUserSplit[1]);

        return groceryUserDTO;
    }

    @Override
    public void logout() {
        preferencesManager.clearPreferences();
    }

    private String prepareToken(String username, String password) {

        StringBuilder builder = new StringBuilder();

        builder.append(username)
                .append(":")
                .append(password);

        return "Basic " + Base64.encodeToString(builder.toString().getBytes(), Base64.NO_WRAP);
    }

    @Override
    public String getFullName() {
        return preferencesManager.getString(FULL_NAME);
    }
}
