package mz.co.commandline.grocery.customer.service;

import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.customer.model.CustomersDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CustomerResource {

    @POST("customers")
    Call<CustomerDTO> registCustomer(@Body CustomerDTO customerDTO);

    @GET("customers/by-unit/{unitUuid}")
    Call<CustomersDTO> findCustomersByUnit(@Path("unitUuid") String unitUuid, @Query("currentPage") int currentPage, @Query("maxResult") int maxResult);

    @GET("customers/pending-payments-by-unit/{unitUuid}")
    Call<CustomersDTO> findCustomersWithPendingPaymentsByUnit(@Path("unitUuid") String unitUuid, @Query("currentPage") int currentPage, @Query("maxResult") int maxResult);

    @GET("customers/find-customers-with-pending-or-incomplete-rent-items-to-return-by-unit/{unitUuid}")
    Call<CustomersDTO> findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(@Path("unitUuid") String unitUuid, @Query("currentPage") int currentPage, @Query("maxResult") int maxResult);

    @GET("customers/pending-contract-payment-by-unit/{unitUuid}")
    Call<CustomersDTO> findCustomersWithContractPendingPaymentByUnit(@Path("unitUuid") String unitUuid, @Query("currentPage") int currentPage, @Query("maxResult") int maxResult, @Query("currentDate") String currentDate);

    @GET("customers/find-customers-sale-with-pendind-or-incomplete-payment-by-unit/{unitUuid}")
    Call<CustomersDTO> findCustomersSaleWithPendindOrIncompletePaymentByUnit(@Path("unitUuid") String unitUuid);

    @GET("customers/find-customers-with-pending-or-incomplete-rentitems-to-load-by-unit/{unitUuid}")
    Call<CustomersDTO> findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(@Path("unitUuid") String unitUuid);

    @GET("customers/find-customers-with-issued-guides-by-type-and-unit")
    Call<CustomersDTO> findCustomersWithIssuedGuidesByTypeAndUnit(@Query("guideType") String guideType, @Query("unitUuid") String unitUuid);

    @GET("customers/find-payments-by-unit/{unitUuid}")
    Call<CustomersDTO> findCustomersWithPaymentsByUnit(@Path("unitUuid") String unitUuid);
}