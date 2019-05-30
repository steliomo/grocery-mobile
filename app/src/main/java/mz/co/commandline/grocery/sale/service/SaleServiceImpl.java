package mz.co.commandline.grocery.sale.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.Listner.ResponseListner;
import mz.co.commandline.grocery.sale.model.Sale;
import mz.co.commandline.grocery.sale.model.SaleReport;
import mz.co.commandline.grocery.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleServiceImpl implements SaleService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public SaleServiceImpl() {
    }

    private SaleResource getResource() {
        return retrofitService.getResource(SaleResource.class);
    }

    @Override
    public void registSale(Sale sale, final ResponseListner<Sale> responseListner) {
        getResource().registeSale(sale).enqueue(new Callback<Sale>() {
            @Override
            public void onResponse(Call<Sale> call, Response<Sale> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setErrorBody(response, responseListner);
            }

            @Override
            public void onFailure(Call<Sale> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    private void setErrorBody(Response response, ResponseListner responseListner) {
        try {
            responseListner.error(response.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void findLast7DaysSales(final ResponseListner<List<SaleReport>> responseListner) {
        getResource().findLast7DaysSales().enqueue(new Callback<List<SaleReport>>() {
            @Override
            public void onResponse(Call<List<SaleReport>> call, Response<List<SaleReport>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setErrorBody(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<SaleReport>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}
