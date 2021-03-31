package mz.co.commandline.grocery.saleable.service;

import java.util.List;

import mz.co.commandline.grocery.saleable.dto.ServiceItemDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceItemResource {

    @GET("service-items/by-service-and-unit")
    Call<List<ServiceItemDTO>> findServiceItemByServiceAndUnit(@Query("serviceUuid") String serviceUuid, @Query("unitUuid") String unitUuid);

    @GET("service-items/not-in-this-unit-by-service")
    Call<List<ServiceItemDTO>> findServiceItemsNotInThisUnitByService(@Query("serviceUuid") String serviceUuid, @Query("unitUuid") String unitUuid);
}
