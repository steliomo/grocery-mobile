package mz.co.commandline.grocery.customer.service;

import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.customer.model.CustomersDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;

public interface CustomerService {

    void registCustomer(CustomerDTO customerDTO, ResponseListner<CustomerDTO> responseListner);

    void findCustomersByUnit(String unitUuid, int currentPage, int maxResult, ResponseListner<CustomersDTO> responseListner);

    void findCustomersWithPendingPaymentsByUnit(String unitUuid, int currentPage, int maxResult, ResponseListner<CustomersDTO> responseListner);

    void findCustomersWithPendingDevlutionsByUnit(String unitUuid, int currentPage, int maxResult, ResponseListner<CustomersDTO> responseListner);
}
