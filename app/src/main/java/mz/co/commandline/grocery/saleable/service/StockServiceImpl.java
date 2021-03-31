package mz.co.commandline.grocery.saleable.service;

import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ProductDTO;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import mz.co.commandline.grocery.saleable.dto.StockDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockServiceImpl extends AbstractService implements StockService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public StockServiceImpl() {
    }

    @Override
    public StockResource getResource() {
        return retrofitService.getResource(StockResource.class);
    }

    @Override
    public void findProductStocksByGroceryAndProduct(GroceryDTO groceryDTO, ProductDTO productDTO, final ResponseListner<List<StockDTO>> responseListner) {
        getResource().findStocksByGroceyAndProduct(groceryDTO.getUuid(), productDTO.getUuid()).enqueue(new Callback<List<StockDTO>>() {
            @Override
            public void onResponse(Call<List<StockDTO>> call, Response<List<StockDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<StockDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findAllStocksByGrocery(GroceryDTO groceryDTO, final ResponseListner<List<StockDTO>> responseListner) {
        getResource().findAllStocksByGrocery(groceryDTO.getUuid()).enqueue(new Callback<List<StockDTO>>() {
            @Override
            public void onResponse(Call<List<StockDTO>> call, Response<List<StockDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<StockDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findLowStocksByGroceryAndSalePeriod(GroceryDTO groceryDTO, String startDate, String endDate, final ResponseListner<List<StockDTO>> responseListner) {
        getResource().findLowStocksByGroceryAndSalePeriod(groceryDTO.getUuid(), startDate, endDate).enqueue(new Callback<List<StockDTO>>() {
            @Override
            public void onResponse(Call<List<StockDTO>> call, Response<List<StockDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<StockDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findProductStocksNotInThisGroceryByProduct(GroceryDTO grocery, ProductDTO productDTO, final ResponseListner<List<StockDTO>> responseListner) {
        getResource().findProductStocksNotInThisGroceryByProduct(grocery.getUuid(), productDTO.getUuid()).enqueue(new Callback<List<StockDTO>>() {
            @Override
            public void onResponse(Call<List<StockDTO>> call, Response<List<StockDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<StockDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}
