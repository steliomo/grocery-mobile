package mz.co.commandline.grocery.rent.service;


import mz.co.commandline.grocery.rent.dto.RentDTO;
import mz.co.commandline.grocery.rent.dto.RentPaymentDTO;
import mz.co.commandline.grocery.rent.dto.RentReport;
import mz.co.commandline.grocery.rent.dto.RentsDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RentResource {

    @POST("rents")
    Call<RentDTO> registRent(@Body RentDTO rentDTO);

    @GET("rents/pending-payments-by-customer/{customerUuid}")
    Call<RentsDTO> findPendingPaymentsRentsByCustomer(@Path("customerUuid") String customerUuid);

    @POST("rents/payments")
    Call<RentPaymentDTO> makePayment(@Body RentPaymentDTO rentPaymentDTO);

    @GET("rents/fetch-rents-with-pending-or-incomplete-rent-item-to-load-by-customer/{customerUuid}")
    Call<RentsDTO> fetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer(@Path("customerUuid") String customerUuid);

    @GET("rents/fetch-rents-with-pending-or-incomplete-rent-item-to-return-by-customer/{customerUuid}")
    Call<RentsDTO> fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(@Path("customerUuid") String customerUuid);

    @GET("rents/fetch-rents-with-payments-by-customer/{customerUuid}")
    Call<RentsDTO> fetchRentsWithPaymentsByCustomer(@Path("customerUuid") String customerUuid);

    @GET("rents/fetch-rents-with-issued-guides-by-type-and-customer")
    Call<RentsDTO> fetchRentsWithIssuedGuidesByTypeAndCustomer(@Query("guideType") String guideType, @Query("customerUuid") String customerUuid);
}
