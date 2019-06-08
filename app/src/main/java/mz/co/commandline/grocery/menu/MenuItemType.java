package mz.co.commandline.grocery.menu;

import mz.co.commandline.grocery.activities.ReportActivity;
import mz.co.commandline.grocery.activities.SaleActivity;
import mz.co.commandline.grocery.activities.StockActivity;

public enum MenuItemType {

    SALE(SaleActivity.class),

    REPORT(ReportActivity.class),

    STOCK(StockActivity.class);

    private final Class clazz;

    MenuItemType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
