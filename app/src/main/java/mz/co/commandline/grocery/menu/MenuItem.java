package mz.co.commandline.grocery.menu;

public class MenuItem {

    private int title;

    private MenuItemType menuItemType;

    private int iconId;

    public MenuItem(int title, MenuItemType menuItemType, int iconId) {
        this.title = title;
        this.menuItemType = menuItemType;
        this.iconId = iconId;
    }

    public int getTitle() {
        return title;
    }

    public MenuItemType getMenuItemType() {
        return menuItemType;
    }


    public int getIconId() {
        return iconId;
    }
}
