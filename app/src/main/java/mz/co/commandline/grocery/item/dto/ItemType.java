package mz.co.commandline.grocery.item.dto;

import mz.co.commandline.grocery.R;

public enum ItemType {

    PRODUCT(R.mipmap.ic_product),

    SERVICE(R.mipmap.ic_services);

    private int iconId;

    ItemType(int iconId) {
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }
}
