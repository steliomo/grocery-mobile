package mz.co.commandline.grocery.sale.service;


import java.util.List;

import mz.co.commandline.grocery.sale.model.Sale;
import mz.co.commandline.grocery.sale.model.SaleReport;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SaleResource {

    @POST("sales")
    Call<Sale> registeSale(@Body Sale sale);

    @GET("sales/last-7-days")
    Call<List<SaleReport>> findLast7DaysSales();
}
