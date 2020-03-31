package mz.co.commandline.grocery.inventory.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

import mz.co.commandline.grocery.model.GenericDTO;

import mz.co.commandline.grocery.stock.dto.StockDTO;

public class StockInventoryDTO extends GenericDTO {

    private StockDTO stockDTO;

    private String fisicalInventory;

    public StockInventoryDTO(StockDTO stockDTO, String fisicalInventory) {
        this.stockDTO = stockDTO;
        this.fisicalInventory = fisicalInventory;
    }

    public StockDTO getStockDTO() {
        return stockDTO;
    }

    public String getFisicalInventory() {
        return fisicalInventory;
    }

    public String getDiffence() {
        return new BigDecimal(stockDTO.getQuantity()).subtract(new BigDecimal(fisicalInventory)).toString();
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
