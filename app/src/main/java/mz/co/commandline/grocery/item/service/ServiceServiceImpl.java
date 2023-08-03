package mz.co.commandline.grocery.item.service;

import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ServiceDTO;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import mz.co.commandline.grocery.saleable.service.ServiceResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceServiceImpl extends AbstractService implements ServiceService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public ServiceServiceImpl() {
    }

    @Override
    public ServiceResource getResource() {
        return retrofitService.getResource(ServiceResource.class);
    }

    @Override
    public void findServiceByUnit(UnitDTO unit, final ResponseListner<List<ServiceDTO>> listResponseListner) {

        getResource().findServicesByUnit(unit.getUuid()).enqueue(new Callback<List<ServiceDTO>>() {
            @Override
            public void onResponse(Call<List<ServiceDTO>> call, Response<List<ServiceDTO>> response) {
                if (response.isSuccessful()) {
                    listResponseListner.success(response.body());
                    return;
                }

                setBodyError(response, listResponseListner);
            }

            @Override
            public void onFailure(Call<List<ServiceDTO>> call, Throwable t) {
                listResponseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void findServicesNotInThisUnit(UnitDTO unit, final ResponseListner<List<ServiceDTO>> listResponseListner) {
        getResource().findServicesNotInThisUnit(unit.getUuid()).enqueue(new Callback<List<ServiceDTO>>() {
            @Override
            public void onResponse(Call<List<ServiceDTO>> call, Response<List<ServiceDTO>> response) {
                if (response.isSuccessful()) {
                    listResponseListner.success(response.body());
                    return;
                }

                setBodyError(response, listResponseListner);
            }

            @Override
            public void onFailure(Call<List<ServiceDTO>> call, Throwable t) {
                listResponseListner.error(t.getMessage());
            }
        });
    }
}
