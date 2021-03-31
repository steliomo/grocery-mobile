package mz.co.commandline.grocery.generics.service;

public interface RetrofitService {

    String BASE_URL = "http://10.0.2.2:8082/services/";

    String AUTH_HEADER = "Authorization";

    <T> T getResource(Class<T> clazz);
}
