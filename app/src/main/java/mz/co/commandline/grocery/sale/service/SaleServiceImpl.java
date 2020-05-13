package mz.co.commandline.grocery.sale.service;

import javax.inject.Inject;

import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SalesDTO;
import mz.co.commandline.grocery.service.AbstractService;
import mz.co.commandline.grocery.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleServiceImpl extends AbstractService implements SaleService {

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

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SaleDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findSalesPerPeriod(String groceryUuid, String startDate, String endDate, final ResponseListner<SalesDTO> responseListner) {
        getResource().findSalesPerPeriod(groceryUuid, startDate, endDate).enqueue(new Callback<SalesDTO>() {
            @Override
            public void onResponse(Call<SalesDTO> call, Response<SalesDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SalesDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findMonthlyalesPerPeriod(String groceryUuid, String startDate, String endDate, final ResponseListner<SalesDTO> responseListner) {
        getResource().findMonthlySalesPerPeriod(groceryUuid, startDate, endDate).enqueue(new Callback<SalesDTO>() {
            @Override
            public void onResponse(Call<SalesDTO> call, Response<SalesDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SalesDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}
