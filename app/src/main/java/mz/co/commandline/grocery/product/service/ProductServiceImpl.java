package mz.co.commandline.grocery.product.service;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.Listner.ResponseListner;
import mz.co.commandline.grocery.product.model.Product;
import mz.co.commandline.grocery.service.RetrofitService;
import mz.co.commandline.grocery.user.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductServiceImpl implements ProductService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public ProductServiceImpl() {
    }

    @NonNull
    private ProductResource getResource() {
        return retrofitService.getResource(ProductResource.class);
    }

    @Override
    public void findAllProducts(final ResponseListner<List<Product>> listner) {

        getResource().findAllProducts().enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    listner.success(response.body());
                    return;
                }

                setErrorBody(response, listner);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                listner.error(t.getMessage());
            }
        });
    }

    private void setErrorBody(Response response, ResponseListner listner) {
        try {
            listner.error(response.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
