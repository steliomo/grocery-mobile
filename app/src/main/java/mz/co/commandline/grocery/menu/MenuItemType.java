package mz.co.commandline.grocery.menu;

import mz.co.commandline.grocery.activities.ExpenseActivity;
import mz.co.commandline.grocery.activities.InventoryActivity;
import mz.co.commandline.grocery.activities.RentActivity;
import mz.co.commandline.grocery.activities.ReportActivity;
import mz.co.commandline.grocery.activities.SaleActivity;
import mz.co.commandline.grocery.activities.SaleableActivity;

public enum MenuItemType {

    SALE(SaleActivity.class),

    REPORT(ReportActivity.class),

    STOCK(SaleableActivity.class),

    INVENTORY(InventoryActivity.class),

    EXPENSE(ExpenseActivity.class),

    RENT(RentActivity.class);

    private final Class clazz;

    MenuItemType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
