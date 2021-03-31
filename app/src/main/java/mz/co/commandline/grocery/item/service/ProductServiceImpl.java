package mz.co.commandline.grocery.item.service;

import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ProductDTO;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductServiceImpl extends AbstractService implements ProductService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public ProductServiceImpl() {
    }

    public ProductResource getResource() {
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

                setBodyError(response, listner);
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                listner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findProductsNotInThisUnit(GroceryDTO groceryDTO, final ResponseListner<List<ProductDTO>> listner) {
        getResource().findProductsNotInGrocery(groceryDTO.getUuid()).enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if (response.isSuccessful()) {
                    listner.success(response.body());
                    return;
                }

                setBodyError(response, listner);
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                listner.error(t.getMessage());
            }
        });
    }
}
