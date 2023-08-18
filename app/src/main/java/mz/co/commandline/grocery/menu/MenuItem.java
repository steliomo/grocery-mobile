package mz.co.commandline.grocery.menu;

public class MenuItem {

    private int title;

    private MenuItemType menuItemType;

    private int iconId;

    private int number;

    public MenuItem(int title, MenuItemType menuItemType, int iconId) {
        this.title = title;
        this.menuItemType = menuItemType;
        this.iconId = iconId;
    }

    public MenuItem(int title, int iconId) {
        this.title = title;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

