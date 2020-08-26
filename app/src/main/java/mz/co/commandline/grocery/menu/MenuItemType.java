package mz.co.commandline.grocery.menu;

import mz.co.commandline.grocery.activities.ExpenseActivity;
import mz.co.commandline.grocery.activities.InventoryActivity;
import mz.co.commandline.grocery.activities.ReportActivity;
import mz.co.commandline.grocery.activities.SaleActivity;
import mz.co.commandline.grocery.activities.ProductsAndStocksActivity;

public enum MenuItemType {

    SALE(SaleActivity.class),

    REPORT(ReportActivity.class),

    STOCK(ProductsAndStocksActivity.class),

    INVENTORY(InventoryActivity.class),

    EXPENSE(ExpenseActivity.class);

    private final Class clazz;

    MenuItemType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
