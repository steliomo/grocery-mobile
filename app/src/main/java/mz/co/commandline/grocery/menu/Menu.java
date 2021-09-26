package mz.co.commandline.grocery.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.user.dto.UserRole;

public class Menu {

    private List<MenuItem> menuItems;

    public Menu() {
        menuItems = new ArrayList<>();
    }

    public List<MenuItem> getMenuItems() {
        return Collections.unmodifiableList(menuItems);
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }
}
