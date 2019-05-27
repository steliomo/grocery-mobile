package mz.co.commandline.grocery.sale;

import org.junit.Test;

import java.math.BigDecimal;

import mz.co.commandline.grocery.sale.model.SaleItem;
import mz.co.commandline.grocery.stock.model.Stock;

import static org.junit.Assert.assertEquals;

public class SaleItemTest {

    @Test
    public void shouldCalculateTotalSaleItem() {

        Stock stock = new Stock();
        stock.setSalePrice(new BigDecimal("10"));
        SaleItem saleItem = new SaleItem(stock, new BigDecimal("2"), new BigDecimal(0), new BigDecimal(2));

        assertEquals(new BigDecimal("18"), saleItem.getTotal());
    }
}
