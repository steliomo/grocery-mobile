package mz.co.commandline.grocery.saleable.service;

import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ServiceDTO;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import mz.co.commandline.grocery.saleable.dto.ServiceItemDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceItemServiceImpl extends AbstractService implements ServiceItemService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public ServiceItemServiceImpl() {
    }

    @Override
    public ServiceItemResource getResource() {
        return retrofitService.getResource(ServiceItemResource.class);
    }

    @Override
    public void findServiceItemsByServiceAndUnit(ServiceDTO service, GroceryDTO unit, final ResponseListner<List<ServiceItemDTO>> responseListner) {

        getResource().findServiceItemByServiceAndUnit(service.getUuid(), unit.getUuid()).enqueue(new Callback<List<ServiceItemDTO>>() {
            @Override
            public void onResponse(Call<List<ServiceItemDTO>> call, Response<List<ServiceItemDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<ServiceItemDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findServiceItemsNotInThisUnitByService(ServiceDTO service, GroceryDTO unit, final ResponseListner<List<ServiceItemDTO>> responseListner) {
        getResource().findServiceItemsNotInThisUnitByService(service.getUuid(), unit.getUuid()).enqueue(new Callback<List<ServiceItemDTO>>() {
            @Override
            public void onResponse(Call<List<ServiceItemDTO>> call, Response<List<ServiceItemDTO>> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<List<ServiceItemDTO>> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}
