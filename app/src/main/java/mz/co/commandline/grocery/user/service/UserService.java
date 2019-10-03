package mz.co.commandline.grocery.user.service;

import mz.co.commandline.grocery.grocery.model.Grocery;
import mz.co.commandline.grocery.grocery.model.GroceryUser;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.user.model.UserDTO;

public interface UserService {

    String getToken();

    void login(String username, String password, ResponseListner<UserDTO> responseListner);

    boolean isLoggedIn();

    Grocery getGrocery();

    GroceryUser getGroceryUser();

    String getFullName();

    void logout();
}
