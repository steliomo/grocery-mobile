package mz.co.commandline.grocery.sale.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SaleReport;
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
    public void registSale(SaleDTO sale, final ResponseListner<SaleDTO> responseListner) {
        getResource().registeSale(sale).enqueue(new Callback<SaleDTO>() {
            @Override
            public void onResponse(Call<SaleDTO> call, Response<SaleDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setErrorBody(response, responseListner);
            }

            @Override
            public void onFailure(Call<SaleDTO> call, Throwable t) {
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
    public void findLast7DaysSales(String groceryUuid, final ResponseListner<List<SaleReport>> responseListner) {
        getResource().findLast7DaysSales(groceryUuid).enqueue(new Callback<List<SaleReport>>() {
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

    @Override
    public void findSalesPerPeriod(String groceryUuid, String startDate, String endDate, final ResponseListner<List<SaleReport>> responseListner) {
        getResource().findSalesPerPeriod(groceryUuid, startDate, endDate).enqueue(new Callback<List<SaleReport>>() {
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
