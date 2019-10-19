package mz.co.commandline.grocery.sale.service;


import java.util.List;

import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SaleReport;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SaleResource {

    @POST("sales")
    Call<SaleDTO> registeSale(@Body SaleDTO sale);

    @GET("sales/last-7-days/{groceryUuid}")
    Call<List<SaleReport>> findLast7DaysSales(@Path("groceryUuid") String groceryUuid);

    @GET("sales/per-period")
    Call<List<SaleReport>> findSalesPerPeriod(@Query("groceryUuid") String groceryUuid, @Query("startDate") String startDate, @Query("endDate") String endDate);
}
