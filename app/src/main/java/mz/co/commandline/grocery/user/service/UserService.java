package mz.co.commandline.grocery.user.service;

public interface UserService {

    String getToken();

    void login(String username, String password);

    boolean isLoggedIn();
}
