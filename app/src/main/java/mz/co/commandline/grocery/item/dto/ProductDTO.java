package mz.co.commandline.grocery.item.dto;

public class ProductDTO extends ItemDTO {

    @Override
    public ItemType getItemType() {
        return ItemType.PRODUCT;
    }
}
