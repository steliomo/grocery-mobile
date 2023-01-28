package mz.co.commandline.grocery.rent.service;

import java.util.List;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.rent.dto.GuideDTO;
import mz.co.commandline.grocery.rent.dto.RentDTO;
import mz.co.commandline.grocery.rent.dto.RentPaymentDTO;
import mz.co.commandline.grocery.rent.dto.RentReport;
import mz.co.commandline.grocery.rent.dto.RentsDTO;
import mz.co.commandline.grocery.rent.dto.ReturnItemDTO;
import okhttp3.ResponseBody;

public interface RentService {

    void registService(RentDTO rentDTO, ResponseListner<RentDTO> responseListner);

    void findPendingPaymentsRentsByCustomer(String customerUuid, ResponseListner<RentsDTO> responseListner);

    void makePayment(RentPaymentDTO rentPaymentDTO, ResponseListner<RentPaymentDTO> responseListner);

    void fetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer(String customerUuid, ResponseListner<RentsDTO> responseListner);

    void fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(String customerUuid, ResponseListner<RentsDTO> responseListner);

    void issueQuotation(RentDTO rentDTO, ResponseListner<RentReport> responseListner);

    void issueTransportGuide(GuideDTO guideDTO, ResponseListner<GuideDTO> responseListner);

    void issueReturnGuide(GuideDTO guideDTO, ResponseListner<GuideDTO> responseListner);
}
