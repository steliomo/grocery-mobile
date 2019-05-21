package mz.co.commandline.grocery.menu;

import mz.co.commandline.grocery.activities.SaleActivity;

public enum MenuItemType {

    SALE(SaleActivity.class),

    REPORT(null),

    STOCK(null);

    private final Class clazz;

    MenuItemType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
