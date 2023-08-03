package mz.co.commandline.grocery.sale.dto;

import android.view.View;

import java.math.BigDecimal;

import mz.co.commandline.grocery.generics.dto.GenericDTO;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.saleable.dto.ServiceItemDTO;
import mz.co.commandline.grocery.saleable.dto.StockDTO;

public class SaleItemDTO extends GenericDTO {

    private StockDTO stockDTO;

    private BigDecimal quantity;

    private BigDecimal saleItemValue;

    private BigDecimal discount;

    private ServiceItemDTO serviceItemDTO;

    private BigDecimal deliveredQuantity;

    private boolean selected;

    private BigDecimal sendQuantity;

    public SaleItemDTO(SaleableItemDTO saleableItemDTO, BigDecimal quantity, BigDecimal saleItemValue, BigDecimal discount) {

        if (ItemType.PRODUCT.equals(saleableItemDTO.getSalableItemType())) {
            this.stockDTO = (StockDTO) saleableItemDTO;
        }

        if (ItemType.SERVICE.equals(saleableItemDTO.getSalableItemType())) {
            serviceItemDTO = (ServiceItemDTO) saleableItemDTO;
        }

        this.quantity = quantity;
        this.saleItemValue = saleItemValue;
        this.discount = discount;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getTotal() {
        return saleItemValue.subtract(discount);
    }

    public SaleableItemDTO getSaleableItemDTO() {
        return stockDTO != null ? stockDTO : serviceItemDTO;
    }

    public BigDecimal getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public BigDecimal getToDeliveryQuantity() {
        return quantity.subtract(deliveredQuantity);
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

    public BigDecimal getSendQuantity() {
        return sendQuantity;
    }

    public void setSendQuantity(BigDecimal sendQuantity) {
        this.sendQuantity = sendQuantity;
    }
}
