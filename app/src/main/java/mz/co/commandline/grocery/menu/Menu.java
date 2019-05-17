package mz.co.commandline.grocery.menu;

import java.util.ArrayList;
import java.util.List;

import mz.co.commandline.grocery.R;

public class Menu {

    private static List<MenuItem> menuItems;

    static {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.string.sales, MenuItemType.SALE, R.mipmap.ic_sale));
        menuItems.add(new MenuItem(R.string.reports, MenuItemType.REPORT, R.mipmap.ic_report));
        menuItems.add(new MenuItem(R.string.stocks, MenuItemType.STOCK, R.mipmap.ic_stock));
    }

    public static List<MenuItem> getMenuItems() {
        return menuItems;
    }
}
