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

    @GET("customers/pending-devolutions-by-unit/{unitUuid}")
    Call<CustomersDTO> findCustomersWithPendingDevolutionsByUnit(@Path("unitUuid") String unitUuid, @Query("currentPage") int currentPage, @Query("maxResult") int maxResult);
}