package mz.co.commandline.grocery.login.delegate;

import mz.co.commandline.grocery.user.dto.UserDTO;

public interface SignUpDelegate {
    void signUpNext(UserDTO userDTO);
}
