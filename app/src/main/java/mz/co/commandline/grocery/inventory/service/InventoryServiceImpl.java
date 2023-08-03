package mz.co.commandline.grocery.inventory.service;

import javax.inject.Inject;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.inventory.dto.InventoryDTO;
import mz.co.commandline.grocery.inventory.dto.InventoryStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryServiceImpl extends AbstractService implements InventoryService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public InventoryServiceImpl() {
    }

    @Override
    public void findInventoryByGroceryAndStatus(UnitDTO grocery, InventoryStatus inventoryStatus, final ResponseListner<InventoryDTO> responseListner) {
        getResource().findInventoryByGroceryAndStatus(grocery.getUuid(), inventoryStatus).enqueue(new Callback<InventoryDTO>() {
            @Override
            public void onResponse(Call<InventoryDTO> call, Response<InventoryDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<InventoryDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void performInventory(InventoryDTO inventory, final ResponseListner<InventoryDTO> responseListner) {
        getResource().performInventory(inventory).enqueue(new Callback<InventoryDTO>() {
            @Override
            public void onResponse(Call<InventoryDTO> call, Response<InventoryDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<InventoryDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void approveInventory(InventoryDTO inventory, final ResponseListner<InventoryDTO> responseListner) {
        getResource().approveInventory(inventory.getUuid()).enqueue(new Callback<InventoryDTO>() {
            @Override
            public void onResponse(Call<InventoryDTO> call, Response<InventoryDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<InventoryDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public InventoryResource getResource() {
        return retrofitService.getResource(InventoryResource.class);
    }
}
