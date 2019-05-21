package mz.co.commandline.grocery.sale.model;

import java.math.BigDecimal;

import mz.co.commandline.grocery.stock.model.Stock;

public class SaleItem {

    private Stock stock;

    private BigDecimal quantity;

    private BigDecimal saleItemValue = BigDecimal.ZERO;

    private BigDecimal discount = BigDecimal.ZERO;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSaleItemValue() {
        return saleItemValue;
    }

    public void setSaleItemValue(BigDecimal saleItemValue) {
        this.saleItemValue = saleItemValue;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
