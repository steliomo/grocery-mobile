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

    @GET("sales/fetch-sales-with-pending-or-incomplete-delivery-status-by-customer/{customerUuid}")
    Call<SalesDTO> fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(@Path("customerUuid") String customerUuid);

    @GET("sales/fetch-sales-with-delivery-guides-by-customer/{customerUuid}")
    Call<SalesDTO> fetchSalesWithDeliveryGuidesByCustomer(@Path("customerUuid") String customerUuid);

    @POST("sales/open-table")
    Call<SaleDTO> processOpenTable(@Body SaleDTO table);

    @GET("sales/fetch-opened-tables")
    Call<SalesDTO> fetchOpenedTables(@Query("unitUuid") String unitUuid);

    @POST("sales/regist-items")
    Call<SaleDTO> registAddedItems(@Body SaleDTO sale);

    @GET("sales/fetch-table-by-uuid/{tableUuid}")
    Call<SaleDTO> fetchOpenedTableByUuid(@Path("tableUuid") String tableUuid);

    @POST("sales/send-table-bill")
    Call<SaleDTO> sendTableBill(@Body SaleDTO table);
}
