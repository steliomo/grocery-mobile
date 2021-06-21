package mz.co.commandline.grocery.payment.service;

import mz.co.commandline.grocery.generics.dto.EnumsDTO;
import mz.co.commandline.grocery.payment.dto.PaymentDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PaymentResource {

    @GET("payments/vouchers")
    Call<EnumsDTO> getVouchers();

    @GET("payments/calculate-payment/{voucher}")
    Call<PaymentDTO> getCalculatedPayment(@Path("voucher") String voucher);

    @POST("payments")
    Call<PaymentDTO> makePayment(@Body PaymentDTO payment);
}
