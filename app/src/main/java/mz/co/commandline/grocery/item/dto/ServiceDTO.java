package mz.co.commandline.grocery.item.dto;

public class ServiceDTO extends ItemDTO {

    @Override
    public ItemType getItemType() {
        return ItemType.SERVICE;
    }
}
