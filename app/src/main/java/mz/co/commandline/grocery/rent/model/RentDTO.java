package mz.co.commandline.grocery.rent.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.generics.dto.GenericDTO;
import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.util.DateUtil;

public class RentDTO extends GenericDTO {

    private GroceryDTO unitDTO;

    private String rentDate;

    private CustomerDTO customerDTO;

    private List<RentItemDTO> rentItemsDTO;

    private BigDecimal totalRent;

    private BigDecimal totalPaid;

    private BigDecimal totalToPay;

    private String fileName;

    private int totalItems;

    public RentDTO(GroceryDTO unit) {
        this.unitDTO = unit;
        rentItemsDTO = new ArrayList<>();
        rentDate = DateUtil.format(new Date(), DateUtil.NORMAL_PATTERN);
    }

    public void addRentItem(RentItemDTO rentItem) {
        rentItemsDTO.add(rentItem);
    }

    public BigDecimal getTotal() {

        BigDecimal total = BigDecimal.ZERO;

        for (RentItemDTO rentItem : rentItemsDTO) {
            total = total.add(rentItem.getValue());
        }
        return total;
    }

    public List<RentItemDTO> getRentItemsDTO() {
        return Collections.unmodifiableList(rentItemsDTO);
    }

    public GroceryDTO getUnitDTO() {
        return unitDTO;
    }

    public void setUnitDTO(GroceryDTO unitDTO) {
        this.unitDTO = unitDTO;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public String getRentDate() {
        return rentDate;
    }

    public BigDecimal getDiscount() {
        BigDecimal discount = BigDecimal.ZERO;

        for (RentItemDTO rentItemDTO : rentItemsDTO) {
            discount = discount.add(rentItemDTO.getDiscount());
        }
        return discount;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public BigDecimal getTotalRent() {
        return totalRent;
    }

    public BigDecimal getTotalToPay() {
        return totalToPay;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public String getFileName() {
        return fileName;
    }
}
