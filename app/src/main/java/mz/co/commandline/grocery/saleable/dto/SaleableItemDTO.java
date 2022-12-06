package mz.co.commandline.grocery.saleable.dto;

import mz.co.commandline.grocery.generics.dto.GenericDTO;
import mz.co.commandline.grocery.item.dto.ItemType;


public abstract class SaleableItemDTO extends GenericDTO {

    public abstract ItemType getSalableItemType();

    public abstract String getName();

    public abstract String getSalePrice();

    public abstract String getRentPrice();
}


