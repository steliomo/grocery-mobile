package mz.co.commandline.grocery.saleable.dto;

import java.math.BigDecimal;

import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.item.dto.ServiceDescriptionDTO;

public class ServiceItemDTO extends SaleableItemDTO {

    private ServiceDescriptionDTO serviceDescriptionDTO;

    private UnitDTO unitDTO;

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

    @Override
    public String getRentPrice() {
        return BigDecimal.ZERO.toString();
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public void setUnitDTO(UnitDTO unitDTO) {
        this.unitDTO = unitDTO;
    }

    public UnitDTO getUnitDTO() {
        return unitDTO;
    }
}
