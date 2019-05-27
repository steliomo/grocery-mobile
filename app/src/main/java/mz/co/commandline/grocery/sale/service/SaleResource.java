package mz.co.commandline.grocery.sale.service;


import mz.co.commandline.grocery.sale.model.Sale;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SaleResource {

    @POST("sales")
    Call<Sale> registeSale(@Body Sale sale);
}
