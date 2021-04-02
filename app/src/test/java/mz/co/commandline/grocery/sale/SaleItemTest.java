package mz.co.commandline.grocery.sale;

import org.junit.Test;

import java.math.BigDecimal;

import mz.co.commandline.grocery.sale.dto.SaleItemDTO;
import mz.co.commandline.grocery.saleable.dto.StockDTO;

import static org.junit.Assert.assertEquals;

public class SaleItemTest {

    @Test
    public void shouldCalculateTotalSaleItem() {

        StockDTO stock = new StockDTO();
        stock.setSalePrice("10");
        SaleItemDTO saleItem = new SaleItemDTO(stock, new BigDecimal("2"), new BigDecimal(20), new BigDecimal(2));

        assertEquals(new BigDecimal("18"), saleItem.getTotal());
    }
}
