package mz.co.commandline.grocery.item.dto;

import mz.co.commandline.grocery.generics.dto.GenericDTO;

public abstract class ItemDTO extends GenericDTO {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract ItemType getItemType();
}
