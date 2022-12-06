package mz.co.commandline.grocery.sale.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.generics.dto.GenericDTO;
import mz.co.commandline.grocery.util.DateUtil;

public class SaleDTO extends GenericDTO {

    private BigDecimal totalSale = BigDecimal.ZERO;

    private List<SaleItemDTO> saleItemsDTO;

    private String saleDate;

    private GroceryDTO groceryDTO;

    private CustomerDTO customerDTO;

    public SaleDTO() {
        this.saleItemsDTO = new ArrayList<>();
        saleDate = DateUtil.format(new Date(), DateUtil.NORMAL_PATTERN);
    }

    public List<SaleItemDTO> getItems() {
        return Collections.unmodifiableList(saleItemsDTO);
    }

    public void addSaleItem(SaleItemDTO saleItem) {
        totalSale = totalSale.add(saleItem.getTotal());
        saleItemsDTO.add(saleItem);
    }

    public BigDecimal getTotalSale() {
        return totalSale;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setGrocery(GroceryDTO groceryDTO) {
        this.groceryDTO = groceryDTO;
    }

    public GroceryDTO getGrocery() {
        return groceryDTO;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}
