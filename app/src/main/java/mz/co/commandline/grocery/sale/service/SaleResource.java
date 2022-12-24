package mz.co.commandline.grocery.sale.service;


import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SalePaymentDTO;
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

    @GET("sales/find-pending-or-incomplete-sales-by-customer/{customerUuid}")
    Call<SalesDTO> findPendingOrIncompleteSalesByCustomer(@Path("customerUuid") String customerUuid);

    @POST("sales/sale-payment")
    Call<SalePaymentDTO> salePayment(@Body SalePaymentDTO salePayment);
}
