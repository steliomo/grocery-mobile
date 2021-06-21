package mz.co.commandline.grocery.payment.service;

import mz.co.commandline.grocery.generics.dto.EnumsDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.payment.dto.PaymentDTO;

public interface PaymentService {

    void getVouchers(ResponseListner<EnumsDTO> responseListner);

    void getCalculatedPayment(String voucher, ResponseListner<PaymentDTO> responseListner);

    void makePayment(PaymentDTO paymentDTO, ResponseListner<PaymentDTO> responseListner);
}
