package mz.co.commandline.grocery.generics.service;

public interface RetrofitService {

    String BASE_URL = "http://10.0.2.2:8082/services/";

    String AUTH_HEADER = "Authorization";

    String LOGIN = "users/login";

    String RESET_PASSWORD = "users/reset-password";

    String SIGN_UP = "users/signup";

    String UNIT_TYPES = "groceries/unit-types";

    <T> T getResource(Class<T> clazz);
}