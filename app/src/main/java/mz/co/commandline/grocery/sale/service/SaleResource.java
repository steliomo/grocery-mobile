package mz.co.commandline.grocery.sale.service;


import java.util.List;

import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SaleReport;
import mz.co.commandline.grocery.sale.dto.SalesDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SaleResource {

    @POST("sales")
    Call<SaleDTO> registeSale(@Body SaleDTO sale);

    @GET("sales/per-period")
    Call<SalesDTO> findSalesPerPeriod(@Query("groceryUuid") String groceryUuid, @Query("startDate") String startDate, @Query("endDate") String endDate);

    @GET("sales/monthly-per-period")
    Call<SalesDTO> findMonthlySalesPerPeriod(@Query("groceryUuid") String groceryUuid, @Query("startDate") String startDate, @Query("endDate") String endDate);
}
