package mz.co.commandline.grocery.rent.dto;

import android.view.View;

import java.math.BigDecimal;

import mz.co.commandline.grocery.generics.dto.GenericDTO;
import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.saleable.dto.ServiceItemDTO;
import mz.co.commandline.grocery.saleable.dto.StockDTO;

public class RentItemDTO extends GenericDTO {

    private SaleableItemDTO saleableItemDTO;

    private StockDTO stockDTO;

    private ServiceItemDTO serviceItemDTO;

    private BigDecimal plannedQuantity;

    private BigDecimal plannedDays;

    private BigDecimal discount;

    private BigDecimal quantity;

    private BigDecimal loadedQuantity;

    private String loadingDate;

    private boolean selected;

    private BigDecimal returnedQuantity;

    private String returnDate;

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

    public BigDecimal getPlannedQuantity() {
        return plannedQuantity;
    }

    public void setPlannedQuantity(BigDecimal plannedQuantity) {
        this.plannedQuantity = plannedQuantity;
    }

    public BigDecimal getPlannedDays() {
        return plannedDays;
    }

    public void setPlannedDays(BigDecimal plannedDays) {
        this.plannedDays = plannedDays;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal calculatePlannedValue() {
        return new BigDecimal(saleableItemDTO.getRentPrice()).multiply(plannedQuantity).multiply(plannedDays).subtract(discount);
    }

    public String getName() {
        if (saleableItemDTO == null) {
            return stockDTO != null ? stockDTO.getName() : serviceItemDTO.getName();
        }
        return saleableItemDTO.getName();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public int getVisiblity() {

        if (selected) {
            return View.VISIBLE;
        }

        return View.GONE;
    }

    public UnitDTO getUnit() {
        return stockDTO != null ? stockDTO.getUnitDTO() : serviceItemDTO.getUnitDTO();
    }

    public BigDecimal getQuantity() {
        return quantity == null ? BigDecimal.ZERO : quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getLoadingDate() {
        return loadingDate == null ? "" : loadingDate;
    }

    public BigDecimal getLoadedQuantity() {
        return loadedQuantity == null ? BigDecimal.ZERO : loadedQuantity;
    }

    public BigDecimal getQuantityToLoad() {
        return plannedQuantity.subtract(getLoadedQuantity());
    }

    public String getReturnDate() {
        return returnDate == null ? "" : returnDate;
    }

    public BigDecimal getReturnedQuantity() {
        return returnedQuantity == null ? BigDecimal.ZERO : returnedQuantity;
    }

    public BigDecimal getQuantityToReturn() {
        return loadedQuantity.subtract(getReturnedQuantity());
    }
}
