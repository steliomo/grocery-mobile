package mz.co.commandline.grocery.login.delegate;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.user.dto.UserDTO;

public interface LoginDelegate extends SignUpDelegate {

    void login(String username, String password);

    void resetPasswordPage();

    void resetPassword(String number);

    void signUp();

    void signUp(GroceryDTO grocery);

    void cancel();
}
