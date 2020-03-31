package mz.co.commandline.grocery.inventory.service;

import mz.co.commandline.grocery.inventory.dto.InventoryDTO;
import mz.co.commandline.grocery.inventory.dto.InventoryStatus;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InventoryResource {

    @GET("inventories/by-grocery-and-status")
    Call<InventoryDTO> findInventoryByGroceryAndStatus(@Query("groceryUuid") String groceryUuid, @Query("inventoryStatus") InventoryStatus inventoryStatus);

    @POST("inventories")
    Call<InventoryDTO> performInventory(@Body InventoryDTO inventory);

    @PUT("inventories/{inventoryUuid}")
    Call<InventoryDTO> approveInventory(@Path("inventoryUuid") String inventoryUuid);
}
