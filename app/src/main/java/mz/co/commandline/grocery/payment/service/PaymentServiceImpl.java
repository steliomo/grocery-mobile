package mz.co.commandline.grocery.payment.service;

import javax.inject.Inject;

import mz.co.commandline.grocery.generics.dto.EnumsDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import mz.co.commandline.grocery.payment.dto.PaymentDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentServiceImpl extends AbstractService implements PaymentService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public PaymentServiceImpl() {
    }

    @Override
    public PaymentResource getResource() {
        return retrofitService.getResource(PaymentResource.class);
    }

    @Override
    public void getVouchers(final ResponseListner<EnumsDTO> responseListner) {
        getResource().getVouchers().enqueue(new Callback<EnumsDTO>() {
            @Override
            public void onResponse(Call<EnumsDTO> call, Response<EnumsDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<EnumsDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void getCalculatedPayment(String voucher, final ResponseListner<PaymentDTO> responseListner) {
        getResource().getCalculatedPayment(voucher).enqueue(new Callback<PaymentDTO>() {
            @Override
            public void onResponse(Call<PaymentDTO> call, Response<PaymentDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<PaymentDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void makePayment(PaymentDTO paymentDTO, final ResponseListner<PaymentDTO> responseListner) {
        getResource().makePayment(paymentDTO).enqueue(new Callback<PaymentDTO>() {
            @Override
            public void onResponse(Call<PaymentDTO> call, Response<PaymentDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<PaymentDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}
