package mz.co.commandline.grocery.saleable.dto;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.item.dto.ServiceDescriptionDTO;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;

public class ServiceItemDTO extends SaleableItemDTO {

    private ServiceDescriptionDTO serviceDescriptionDTO;

    private GroceryDTO unitDTO;

    private String salePrice;

    private String name;

    public ServiceItemDTO() {
        super();
    }

    @Override
    public ItemType getSalableItemType() {
        return ItemType.SERVICE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public void setUnitDTO(GroceryDTO unitDTO) {
        this.unitDTO = unitDTO;
    }
}
