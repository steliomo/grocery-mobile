package mz.co.commandline.grocery.customer.delegate;

import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.customer.model.CustomersDTO;

public interface CustomerDelegate {

    void addCustomer();

    void registCustomer(CustomerDTO CustomerDTO);

    CustomersDTO getCustomersDTO();

    void selectedCustomer(CustomerDTO customerDTO);

    int addBtnVisibility();
}
