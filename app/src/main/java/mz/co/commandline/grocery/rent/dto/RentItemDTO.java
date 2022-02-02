package mz.co.commandline.grocery.rent.dto;

import android.view.View;

import java.math.BigDecimal;

import mz.co.commandline.grocery.generics.dto.GenericDTO;
import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.saleable.dto.ServiceItemDTO;
import mz.co.commandline.grocery.saleable.dto.StockDTO;
import mz.co.commandline.grocery.util.DateUtil;

public class RentItemDTO extends GenericDTO {

    private SaleableItemDTO saleableItemDTO;

    private StockDTO stockDTO;

    private ServiceItemDTO serviceItemDTO;

    private BigDecimal quantity;

    private String startDate;

    private String endDate;

    private Long days;

    private BigDecimal value;

    private BigDecimal discount;

    private BigDecimal returned;

    private BigDecimal toReturn;

    private boolean selected;

    private Boolean returnable;

    private String description;

    public RentItemDTO(SaleableItemDTO saleableItemDTO) {
        this.saleableItemDTO = saleableItemDTO;
        discount = BigDecimal.ZERO;
        if (ItemType.PRODUCT.equals(saleableItemDTO.getSalableItemType())) {
            stockDTO = (StockDTO) saleableItemDTO;
            return;
        }
        serviceItemDTO = (ServiceItemDTO) saleableItemDTO;
    }

    public SaleableItemDTO getSaleableItemDTO() {
        return saleableItemDTO;
    }

    public StockDTO getStockDTO() {
        return stockDTO;
    }

    public ServiceItemDTO getServiceItemDTO() {
        return serviceItemDTO;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getDays() {
        return days;
    }

    public void setDays() {
        this.days = DateUtil.daysBetween(startDate, endDate);
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void calculateValue() {
        value = new BigDecimal(saleableItemDTO.getSalePrice()).multiply(quantity).multiply(BigDecimal.valueOf(days)).subtract(discount);
    }

    public String getName() {
        if (saleableItemDTO == null) {
            return stockDTO != null ? stockDTO.getName() : serviceItemDTO.getName();
        }
        return saleableItemDTO.getName();
    }

    public Boolean isEndDateValid() {
        try {
            long daysBetween = DateUtil.daysBetween(startDate, endDate);
            if (daysBetween == BigDecimal.ZERO.longValue()) {
                return Boolean.FALSE;
            }
        } catch (IllegalArgumentException e) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public BigDecimal getReturned() {
        return returned;
    }

    public BigDecimal getToReturn() {
        return toReturn;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getVisiblity() {

        if (selected) {
            return View.VISIBLE;
        }

        return View.GONE;
    }

    public Boolean getReturnable() {
        return returnable;
    }

    public GroceryDTO getUnit() {
        return stockDTO != null ? stockDTO.getGroceryDTO() : serviceItemDTO.getUnitDTO();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
