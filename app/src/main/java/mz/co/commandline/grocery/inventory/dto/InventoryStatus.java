package mz.co.commandline.grocery.inventory.dto;

import mz.co.commandline.grocery.R;

public enum InventoryStatus {

    PENDING(R.string.pending),

    APPROVED(R.string.approved);

    private int value;

    InventoryStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
