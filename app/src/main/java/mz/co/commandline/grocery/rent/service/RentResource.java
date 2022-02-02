package mz.co.commandline.grocery.rent.service;


import java.util.List;

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

    @GET("rents/pending-payments-by-customer{customerUuid}")
    Call<RentsDTO> findPendingPaymentsRentsByCustomer(@Path("customerUuid") String customerUuid);

    @POST("rents/payments")
    Call<RentPaymentDTO> makePayment(@Body RentPaymentDTO rentPaymentDTO);

    @GET("rents/pending-devolutions-by-customer{customerUuid}")
    Call<RentsDTO> fetchPendingDevolutionsRentsByCustomer(@Path("customerUuid") String customerUuid);

    @POST("rents/return-items")
    Call<List<ReturnItemDTO>> returnItems(@Body List<ReturnItemDTO> returnItemsDTO);

    @GET("rents/quotation/{fileName}")
    Call<ResponseBody> getQuotationFile(@Path("fileName") String fileName);

    @POST("rents/process-quotation")
    Call<RentReport> processtQuotation(@Body RentDTO rentDTO);
}
