package mz.co.commandline.grocery.saleable.service;

import java.util.List;

import mz.co.commandline.grocery.item.dto.ServiceDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceResource {

    @GET("services/by-unit/{unitUuid}")
    Call<List<ServiceDTO>> findServicesByUnit(@Path("unitUuid") String unitUuid);

    @GET("services/not-in-this-unit/{unitUuid}")
    Call<List<ServiceDTO>> findServicesNotInThisUnit(@Path("unitUuid") String unitUuid);
}
