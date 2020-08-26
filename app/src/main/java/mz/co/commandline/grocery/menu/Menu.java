package mz.co.commandline.grocery.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.user.dto.UserRole;

public class Menu {

    private List<MenuItem> menuItems;

    public Menu(UserRole userRole) {

        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.string.sales, MenuItemType.SALE, R.mipmap.ic_sale));

        if (UserRole.OPERATOR != userRole) {
            menuItems.add(new MenuItem(R.string.products_and_stocks, MenuItemType.STOCK, R.mipmap.ic_stock));
            menuItems.add(new MenuItem(R.string.expenses, MenuItemType.EXPENSE, R.mipmap.ic_expense));
        }

        menuItems.add(new MenuItem(R.string.inventory, MenuItemType.INVENTORY, R.mipmap.ic_inventory));
        menuItems.add(new MenuItem(R.string.reports, MenuItemType.REPORT, R.mipmap.ic_report));
    }

    public List<MenuItem> getMenuItems() {
        return Collections.unmodifiableList(menuItems);
    }
}
