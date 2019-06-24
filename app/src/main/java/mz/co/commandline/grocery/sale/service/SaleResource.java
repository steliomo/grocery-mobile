package mz.co.commandline.grocery.sale.service;


import java.util.List;

import mz.co.commandline.grocery.sale.model.Sale;
import mz.co.commandline.grocery.sale.model.SaleReport;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SaleResource {

    @POST("sales")
    Call<Sale> registeSale(@Body Sale sale);

    @GET("sales/last-7-days")
    Call<List<SaleReport>> findLast7DaysSales();

    @GET("sales/per-period")
    Call<List<SaleReport>> findSalesPerPeriod(@Query("startDate") String startDate, @Query("endDate") String endDate);
}
