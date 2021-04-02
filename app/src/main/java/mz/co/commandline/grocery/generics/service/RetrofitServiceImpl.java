package mz.co.commandline.grocery.generics.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import mz.co.commandline.grocery.infra.SharedPreferencesManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceImpl implements RetrofitService {

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    private Retrofit retrofit;

    @Inject
    public RetrofitServiceImpl() {
    }

    private OkHttpClient configureClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        return new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        if (chain.request().url().toString().contains(LOGIN)
                                || chain.request().url().toString().contains(RESET_PASSWORD)
                                || chain.request().url().toString().contains(SIGN_UP)
                                || chain.request().url().toString().contains(UNIT_TYPES)
                        ) {
                            return chain.proceed(chain.request());
                        }

                        Request request = chain.request().newBuilder().header(AUTH_HEADER, sharedPreferencesManager.getToken()).build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    private Retrofit build() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(configureClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public <T> T getResource(Class<T> clazz) {

        if (retrofit == null) {
            retrofit = build();
        }

        return retrofit.create(clazz);
    }
}
