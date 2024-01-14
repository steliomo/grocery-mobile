package mz.co.commandline.grocery.sale.dto;

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

public class SaleDTO extends GenericDTO {

    private List<SaleItemDTO> saleItemsDTO;

    private String saleDate;

    private UnitDTO unitDTO;

    private CustomerDTO customerDTO;

    private SaleType saleType;

    private BigDecimal totalPaid;

    private String dueDate;

    private EnumDTO saleStatus;

    private BigDecimal total;

    private String lastPaymentDate;

    private EnumDTO deliveryStatus;

    private List<GuideDTO> guidesDTO;

    private int tableNumber;

    public SaleDTO() {
        total = BigDecimal.ZERO;
        this.saleItemsDTO = new ArrayList<>();
        saleDate = DateUtil.format(new Date(), DateUtil.NORMAL_PATTERN);
    }

    public List<SaleItemDTO> getItems() {
        return Collections.unmodifiableList(saleItemsDTO);
    }

    public void addSaleItem(SaleItemDTO saleItem) {
        total = total.add(saleItem.getTotal());
        saleItemsDTO.add(saleItem);
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setUnitDTO(UnitDTO unitDTO) {
        this.unitDTO = unitDTO;
    }

    public UnitDTO getUnitDTO() {
        return unitDTO;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public void setSaleType(SaleType saleType) {
        this.saleType = saleType;
    }

    public SaleType getSaleType() {
        return saleType;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid == null ? new BigDecimal(0) : totalPaid;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public EnumDTO getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(EnumDTO saleStatus) {
        this.saleStatus = saleStatus;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public EnumDTO getDeliveryStatus() {
        return deliveryStatus;
    }

    public List<GuideDTO> getGuidesDTO() {
        return guidesDTO;
    }

    public void setGuidesDTO(List<GuideDTO> guidesDTO) {
        this.guidesDTO = guidesDTO;
    }

    public BigDecimal totalToPay() {
        return total.subtract(getTotalPaid());
    }

    public void cleanItems() {
        saleItemsDTO = new ArrayList<>();
    }

    public void removeAll(List<SaleItemDTO> items) {
        for (SaleItemDTO saleItem : items) {
            total = total.subtract(saleItem.getTotal());
            saleItemsDTO.remove(saleItem);
        }
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }
}
