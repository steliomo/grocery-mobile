package mz.co.commandline.grocery.product.service;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.product.dto.ProductDTO;
import mz.co.commandline.grocery.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void findProductsByGrocery(GroceryDTO groceryDTO, final ResponseListner<List<ProductDTO>> listner) {

        getResource().findProductsByGrocery(groceryDTO.getUuid()).enqueue(new Callback<List<ProductDTO>>() {

            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if (response.isSuccessful()) {
                    listner.success(response.body());
                    return;
                }

                setErrorBody(response, listner);
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                listner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findProductsNotInThisGrocery(GroceryDTO groceryDTO, final ResponseListner<List<ProductDTO>> listner) {
        getResource().findProductsNotInGrocery(groceryDTO.getUuid()).enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if (response.isSuccessful()) {
                    listner.success(response.body());
                    return;
                }

                setErrorBody(response, listner);
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
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
