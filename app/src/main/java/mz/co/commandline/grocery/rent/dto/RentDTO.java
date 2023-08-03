package mz.co.commandline.grocery.rent.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.generics.dto.EnumDTO;
import mz.co.commandline.grocery.generics.dto.GenericDTO;
import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.guide.dto.GuideDTO;
import mz.co.commandline.grocery.util.DateUtil;

public class RentDTO extends GenericDTO {

    private UnitDTO unitDTO;

    private String rentDate;

    private CustomerDTO customerDTO;

    private List<RentItemDTO> rentItemsDTO;

    private BigDecimal totalEstimated;

    private BigDecimal totalCalculated;

    private BigDecimal totalPaid;

    private BigDecimal totalToPay;

    private BigDecimal totalToRefund;

    private String fileName;

    private int totalItems;

    private EnumDTO paymentStatus;

    private List<GuideDTO> guidesDTO;

    private List<RentPaymentDTO> rentPaymentsDTO;

    public RentDTO(UnitDTO unit) {
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
            total = total.add(rentItem.calculatePlannedValue());
        }
        return total;
    }

    public List<RentItemDTO> getRentItemsDTO() {
        return Collections.unmodifiableList(rentItemsDTO);
    }

    public UnitDTO getUnitDTO() {
        return unitDTO;
    }

    public void setUnitDTO(UnitDTO unitDTO) {
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

    public BigDecimal getTotalEstimated() {
        return totalEstimated;
    }

    public BigDecimal getTotalCalculated() {
        return totalCalculated;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public BigDecimal getTotalToPay() {
        return totalToPay;
    }

    public BigDecimal getTotalToRefund() {
        return totalToRefund;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public String getFileName() {
        return fileName;
    }

    public EnumDTO getPaymentStatus() {
        return paymentStatus;
    }

    public List<GuideDTO> getGuidesDTO() {
        return guidesDTO;
    }

    public void setGuidesDTO(List<GuideDTO> guidesDTO) {
        this.guidesDTO = guidesDTO;
    }

    public void setRentItemsDTO(List<RentItemDTO> rentItemsDTO) {
        this.rentItemsDTO = rentItemsDTO;
    }

    public List<RentPaymentDTO> getRentPaymentsDTO() {
        return rentPaymentsDTO;
    }
}
