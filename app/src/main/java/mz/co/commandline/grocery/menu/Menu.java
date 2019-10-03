package mz.co.commandline.grocery.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.user.model.UserRole;

public class Menu {

    private List<MenuItem> menuItems;

    public Menu(UserRole userRole) {

        menuItems = new ArrayList<>();

        menuItems.add(new MenuItem(R.string.sales, MenuItemType.SALE, R.mipmap.ic_sale));
        menuItems.add(new MenuItem(R.string.reports, MenuItemType.REPORT, R.mipmap.ic_report));

        if (UserRole.OPERATOR != userRole) {
            menuItems.add(new MenuItem(R.string.stocks, MenuItemType.STOCK, R.mipmap.ic_stock));
        }
    }

    public List<MenuItem> getMenuItems() {
        return Collections.unmodifiableList(menuItems);
    }
}
