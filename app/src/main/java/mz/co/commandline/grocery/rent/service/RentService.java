package mz.co.commandline.grocery.rent.service;

import java.util.List;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.rent.model.RentDTO;
import mz.co.commandline.grocery.rent.model.RentPaymentDTO;
import mz.co.commandline.grocery.rent.model.RentReport;
import mz.co.commandline.grocery.rent.model.RentsDTO;
import mz.co.commandline.grocery.rent.model.ReturnItemDTO;
import okhttp3.ResponseBody;

public interface RentService {

    void registService(RentDTO rentDTO, ResponseListner<RentDTO> responseListner);

    void findPendingPaymentsRentsByCustomer(String customerUuid, ResponseListner<RentsDTO> responseListner);

    void makePayment(RentPaymentDTO rentPaymentDTO, ResponseListner<RentPaymentDTO> responseListner);

    void fetchPendingDevolutionsRentsByCustomer(String customerUuid, ResponseListner<RentsDTO> responseListner);

    void returnItems(List<ReturnItemDTO> returnItemsDTO, ResponseListner<List<ReturnItemDTO>> responseListner);

    void getQuotationFile(String fileName, ResponseListner<ResponseBody> responseListner);

    void processtQuotation(RentDTO rentDTO, ResponseListner<RentReport> responseListner);
}
