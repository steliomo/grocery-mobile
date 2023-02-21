package mz.co.commandline.grocery.customer.service;

import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.customer.model.CustomersDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;

public interface CustomerService {

    void registCustomer(CustomerDTO customerDTO, ResponseListner<CustomerDTO> responseListner);

    void findCustomersByUnit(String unitUuid, int currentPage, int maxResult, ResponseListner<CustomersDTO> responseListner);

    void findCustomersWithPendingPaymentsByUnit(String unitUuid, int currentPage, int maxResult, ResponseListner<CustomersDTO> responseListner);

    void findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(String unitUuid, int currentPage, int maxResult, ResponseListner<CustomersDTO> responseListner);

    void findCustomersWithContractPendingPaymentByUnit(String unitUuid, int currentPage, int maxResult, String currentDate, ResponseListner<CustomersDTO> responseListner);

    void findCustomersSaleWithPendindOrIncompletePaymentByUnit(String unitUuid, ResponseListner<CustomersDTO> responseListner);

    void findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(String unitUuid, ResponseListner<CustomersDTO> responseListner);

    void findCustomersWithIssuedGuidesByTypeAndUnit(String guideType, String unitUuid, ResponseListner<CustomersDTO> responseListner);

    void findCustomersWithPaymentsByUnit(String unitUuid, ResponseListner<CustomersDTO> responseListner);

    void findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(String unitUuid, ResponseListner<CustomersDTO> responseListner);

    void findCustomersWithDeliveredGuidesByUnit(String unitUuid, ResponseListner<CustomersDTO> responseListner);
}
