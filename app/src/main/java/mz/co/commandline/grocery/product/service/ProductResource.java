package mz.co.commandline.grocery.product.service;

import java.util.List;

import mz.co.commandline.grocery.product.model.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProductResource {

    @GET("products")
    Call<List<Product>> findAllProducts();
}
