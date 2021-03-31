package mz.co.commandline.grocery.saleable.service;

import mz.co.commandline.grocery.saleable.dto.SaleableDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SaleableResource {

    @POST("saleables")
    Call<SaleableDTO> addSaleable(@Body SaleableDTO saleableDTO);


    @PUT("saleables")
    Call<SaleableDTO> updateSaleable(@Body SaleableDTO saleableDTO);
}
