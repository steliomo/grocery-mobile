package mz.co.commandline.grocery.sale.model;

import java.math.BigDecimal;

import mz.co.commandline.grocery.stock.model.Stock;

public class SaleItem {

    private Stock stock;

    private BigDecimal quantity;

    private BigDecimal saleItemValue;

    private BigDecimal discount;

    public SaleItem(Stock stock, BigDecimal quantity, BigDecimal saleItemValue, BigDecimal discount) {
        this.stock = stock;
        this.quantity = quantity;
        this.saleItemValue = saleItemValue;
        this.discount = discount;
    }

    public Stock getStock() {
        return stock;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getSaleItemValue() {
        return saleItemValue;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getTotal() {
        return stock.getSalePrice().multiply(quantity).subtract(discount);
    }
}
