package mz.co.commandline.grocery.sale.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sale {

    private List<SaleItem> saleItems;

    public Sale() {
        this.saleItems = new ArrayList<>();
    }

    public List<SaleItem> getSaleItems() {
        return Collections.unmodifiableList(saleItems);
    }

    public void AddSaleItem(SaleItem saleItem) {
        saleItems.add(saleItem);
    }
}
