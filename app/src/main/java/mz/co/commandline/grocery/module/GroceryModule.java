package mz.co.commandline.grocery.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mz.co.commandline.grocery.infra.SharedPreferencesManager;
import mz.co.commandline.grocery.product.service.ProductService;
import mz.co.commandline.grocery.product.service.ProductServiceImpl;
import mz.co.commandline.grocery.service.RetrofitService;
import mz.co.commandline.grocery.service.RetrofitServiceImpl;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.user.service.UserServiceImpl;
import retrofit2.Retrofit;

@Module
public class GroceryModule {

    private Context contenxt;

    public GroceryModule(Context contenxt) {
        this.contenxt = contenxt;
    }

    @Singleton
    @Provides
    public RetrofitService provideRetrofitService(RetrofitServiceImpl retrofitService) {
        return retrofitService;
    }

    @Singleton
    @Provides
    public SharedPreferencesManager provideSharedPreferencesManager() {
        return new SharedPreferencesManager(contenxt);
    }

    @Provides
    public ProductService provideProductService(ProductServiceImpl productService) {
        return productService;
    }

    @Provides
    public UserService provideUserService(UserServiceImpl userService) {
        return userService;
    }
}
