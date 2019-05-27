package mz.co.commandline.grocery.sale.service;

import javax.inject.Inject;

import mz.co.commandline.grocery.Listner.ResponseListner;
import mz.co.commandline.grocery.sale.model.Sale;
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
                responseListner.success(response.body());
            }

            @Override
            public void onFailure(Call<Sale> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}
