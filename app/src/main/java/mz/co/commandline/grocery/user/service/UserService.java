package mz.co.commandline.grocery.user.service;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.user.dto.GroceryUserDTO;
import mz.co.commandline.grocery.user.dto.UserDTO;

public interface UserService {

    String getToken();

    void login(String username, String password, ResponseListner<UserDTO> responseListner);

    boolean isLoggedIn();

    GroceryDTO getGroceryDTO();

    GroceryUserDTO getGroceryUser();

    String getFullName();

    void logout();

    void resetPassword(UserDTO user, ResponseListner<UserDTO> responseListner);

    void signUp(UserDTO user, ResponseListner<UserDTO> responseListner);

    void addSaler(UserDTO user, ResponseListner<UserDTO> responseListner);
}
