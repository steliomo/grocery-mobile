package mz.co.commandline.grocery.service;

public interface RetrofitService {

    String BASE_URL = "https://mercearias.rnds.co.mz/services/";

    String AUTH_HEADER = "Authorization";

    <T> T getResource(Class<T> clazz);
}
