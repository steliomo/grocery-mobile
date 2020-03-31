package mz.co.commandline.grocery.product.service;

import java.util.List;

import mz.co.commandline.grocery.product.dto.ProductDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductResource {

    @GET("products/by-grocery/{groceryUuid}")
    Call<List<ProductDTO>> findProductsByGrocery(@Path("groceryUuid") String groceryUuid);
}
