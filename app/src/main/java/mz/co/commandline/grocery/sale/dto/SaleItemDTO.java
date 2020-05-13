package mz.co.commandline.grocery.sale.dto;

import java.math.BigDecimal;

import mz.co.commandline.grocery.stock.dto.StockDTO;

public class SaleItemDTO {

    private StockDTO stockDTO;

    private BigDecimal quantity;

    private BigDecimal saleItemValue;

    private BigDecimal discount;

    public SaleItemDTO(StockDTO stockDTO, BigDecimal quantity, BigDecimal saleItemValue, BigDecimal discount) {
        this.stockDTO = stockDTO;
        this.quantity = quantity;
        this.saleItemValue = saleItemValue;
        this.discount = discount;
    }

    public StockDTO getStockDTO() {
        return stockDTO;
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
}
