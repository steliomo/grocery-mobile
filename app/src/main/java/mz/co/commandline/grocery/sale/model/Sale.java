package mz.co.commandline.grocery.sale.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sale {

    private BigDecimal totalSale = BigDecimal.ZERO;

    private List<SaleItem> items;

    public Sale() {
        this.items = new ArrayList<>();
    }

    public List<SaleItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addSaleItem(SaleItem saleItem) {
        totalSale = totalSale.add(saleItem.getTotal());
        items.add(saleItem);
    }

    public BigDecimal getTotalSale() {
        return totalSale;
    }
}
