package mz.co.commandline.grocery.customer.model;

import java.util.ArrayList;
import java.util.List;

public class CustomersDTO {

    private List<CustomerDTO> customerDTOs;

    private Long totalCustomers;

    public List<CustomerDTO> getCustomerDTOs() {

        if (customerDTOs == null) {
            return new ArrayList<>();
        }
        return this.customerDTOs;
    }

    public Long getTotalCustomers() {
        return this.totalCustomers;
    }
}
