package mz.co.commandline.grocery.stock.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.grocery.model.Grocery;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.product.model.Product;
import mz.co.commandline.grocery.service.RetrofitService;
import mz.co.commandline.grocery.stock.model.Stock;
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
    public void findProductStocksByGroceryAndProduct(Grocery grocery, Product product, final ResponseListner<List<Stock>> responseListner) {
        getResource().findStocksByGroceyAndProduct(grocery.getUuid(), product.getUuid()).enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setErrorBody(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findAllStocksByGrocery(Grocery grocery, final ResponseListner<List<Stock>> responseListner) {
        getResource().findAllStocksByGrocery(grocery.getUuid()).enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setErrorBody(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void updateStocksAndPrices(List<Stock> stocks, final ResponseListner<Void> responseListner) {
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
