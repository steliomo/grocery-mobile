package mz.co.commandline.grocery.stock.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.Listner.ResponseListner;
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
    public void findProductStocksByProduct(Product product, final ResponseListner<List<Stock>> responseListner) {
        getResource().findStocksByProduct(product.getUuid()).enqueue(new Callback<List<Stock>>() {
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
    public void findAllStocks(final ResponseListner<List<Stock>> responseListner) {
        getResource().findAllStocks().enqueue(new Callback<List<Stock>>() {
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

}
