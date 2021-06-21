package mz.co.commandline.grocery.payment.delegate;

import java.util.List;

import mz.co.commandline.grocery.generics.dto.EnumDTO;
import mz.co.commandline.grocery.payment.dto.PaymentDTO;
import mz.co.commandline.grocery.user.dto.UnitDetail;
import mz.co.commandline.grocery.user.dto.UserDTO;

public interface PaymentDelegate {

    void mpesaMethod();

    void proceed();

    PaymentDTO getPayment();

    void paymentExecution(PaymentDTO payment);

    List<EnumDTO> getVouchers();

    UnitDetail getUnitDetail();
}
