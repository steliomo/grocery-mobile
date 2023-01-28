package mz.co.commandline.grocery.rent.service;


import java.util.List;

import mz.co.commandline.grocery.rent.dto.GuideDTO;
import mz.co.commandline.grocery.rent.dto.RentDTO;
import mz.co.commandline.grocery.rent.dto.RentPaymentDTO;
import mz.co.commandline.grocery.rent.dto.RentReport;
import mz.co.commandline.grocery.rent.dto.RentsDTO;
import mz.co.commandline.grocery.rent.dto.ReturnItemDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @POST("rents/issue-quotation")
    Call<RentReport> issueQuotation(@Body RentDTO rentDTO);

    @POST("rents/issue-transport-guide")
    Call<GuideDTO> issueTransportGuide(@Body GuideDTO guideDTO);

    @POST("rents/issue-return-guide")
    Call<GuideDTO> issueReturnGuide(@Body GuideDTO guideDTO);
}
