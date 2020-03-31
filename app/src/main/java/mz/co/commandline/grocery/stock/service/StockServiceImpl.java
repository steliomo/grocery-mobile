package mz.co.commandline.grocery.stock.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.product.dto.ProductDTO;
import mz.co.commandline.grocery.service.RetrofitService;
import mz.co.commandline.grocery.stock.dto.StockDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockServiceImpl implements StockService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public StockServiceImpl() {
    }

    private StockResource getResource() {
        return retrofitService.getResource(StockResource.class);
    }

    private void setErrorBody(Response response, ResponseListner responseListner) {
        try {
            responseListner.error(response.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

                setErrorBody(response, responseListner);
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

                setErrorBody(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<StockDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void updateStocksAndPrices(List<StockDTO> stocks, final ResponseListner<Void> responseListner) {
        getResource().updateStocksAndPrices(stocks).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setErrorBody(response, responseListner);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}
