package mz.co.commandline.grocery.customer.model;

import java.util.List;

public class CustomersDTO {

    private List<CustomerDTO> customerDTOs;

    private Long totalCustomers;

    public List<CustomerDTO> getCustomerDTOs() {
        return this.customerDTOs;
    }

    public Long getTotalCustomers() {
        return this.totalCustomers;
    }
}
