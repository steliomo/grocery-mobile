package mz.co.commandline.grocery.sale.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import mz.co.commandline.grocery.grocery.model.Grocery;
import mz.co.commandline.grocery.util.DateUtil;

public class Sale {

    private BigDecimal totalSale = BigDecimal.ZERO;

    private List<SaleItem> items;

    private String saleDate;

    private Grocery grocery;

    public Sale() {
        this.items = new ArrayList<>();
        saleDate = DateUtil.format(new Date(), DateUtil.NORMAL_PATTERN);
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

    public String getSaleDate() {
        return saleDate;
    }

    public void setGrocery(Grocery grocery) {
        this.grocery = grocery;
    }

    public Grocery getGrocery() {
        return grocery;
    }
}
