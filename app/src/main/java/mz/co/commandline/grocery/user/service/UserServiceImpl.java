package mz.co.commandline.grocery.user.service;

import android.util.Base64;

import javax.inject.Inject;

import mz.co.commandline.grocery.infra.SharedPreferencesManager;

public class UserServiceImpl implements UserService {

    private static final String TOKEN = "TOKEN";

    @Inject
    SharedPreferencesManager preferencesManager;

    @Inject
    public UserServiceImpl() {
    }

    @Override
    public String getToken() {
        return preferencesManager.getString(TOKEN);
    }

    @Override
    public void login(String username, String password) {
        String token = prepareToken(username, password);
        preferencesManager.storeString(TOKEN, token);
    }

    @Override
    public boolean isLoggedIn() {

        String token = preferencesManager.getString(TOKEN);

        if (token != null) {
            return true;
        }

        return false;
    }

    private String prepareToken(String username, String password) {

        StringBuilder builder = new StringBuilder();

        builder.append(username)
                .append(":")
                .append(password);

        return "Basic " + Base64.encodeToString(builder.toString().getBytes(), Base64.NO_WRAP);
    }
}
