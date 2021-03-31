package mz.co.commandline.grocery.inventory.service;

import java.io.IOException;

import javax.inject.Inject;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.inventory.dto.InventoryDTO;
import mz.co.commandline.grocery.inventory.dto.InventoryStatus;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryServiceImpl implements InventoryService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public InventoryServiceImpl() {
    }

    @Override
    public void findInventoryByGroceryAndStatus(GroceryDTO grocery, InventoryStatus inventoryStatus, final ResponseListner<InventoryDTO> responseListner) {
        getResource().findInventoryByGroceryAndStatus(grocery.getUuid(), inventoryStatus).enqueue(new Callback<InventoryDTO>() {
            @Override
            public void onResponse(Call<InventoryDTO> call, Response<InventoryDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setErrorBody(response, responseListner);
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

                setErrorBody(response, responseListner);
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
                setErrorBody(response, responseListner);
            }

            @Override
            public void onFailure(Call<InventoryDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    private void setErrorBody(Response<InventoryDTO> response, ResponseListner<InventoryDTO> responseListner) {
        try {
            responseListner.error(response.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private InventoryResource getResource() {
        return retrofitService.getResource(InventoryResource.class);
    }
}
