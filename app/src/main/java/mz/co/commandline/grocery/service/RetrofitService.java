package mz.co.commandline.grocery.service;

import java.io.IOException;

import javax.inject.Inject;

import mz.co.commandline.grocery.user.service.UserService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface RetrofitService {

    String BASE_URL = "http://10.0.2.2:8082/services/";

    String AUTH_HEADER = "Authorization";

    <T> T getResource(Class<T> clazz);
}
