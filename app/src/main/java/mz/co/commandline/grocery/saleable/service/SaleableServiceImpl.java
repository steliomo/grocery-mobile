package mz.co.commandline.grocery.saleable.service;

import javax.inject.Inject;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.saleable.dto.SaleableDTO;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleableServiceImpl extends AbstractService implements SaleableService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public SaleableServiceImpl() {
    }

    @Override
    public void addSaleable(SaleableDTO saleable, final ResponseListner<SaleableDTO> responseListner) {
        getResource().addSaleable(saleable).enqueue(new Callback<SaleableDTO>() {
            @Override
            public void onResponse(Call<SaleableDTO> call, Response<SaleableDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SaleableDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void updateSaleable(SaleableDTO saleable, final ResponseListner<SaleableDTO> responseListner) {
        getResource().updateSaleable(saleable).enqueue(new Callback<SaleableDTO>() {
            @Override
            public void onResponse(Call<SaleableDTO> call, Response<SaleableDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<SaleableDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public SaleableResource getResource() {
        return retrofitService.getResource(SaleableResource.class);
    }
}
