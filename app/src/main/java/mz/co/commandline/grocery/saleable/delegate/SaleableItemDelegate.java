package mz.co.commandline.grocery.saleable.delegate;

import java.util.List;

import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.item.dto.ItemType;

public interface SaleableItemDelegate {

    List<SaleableItemDTO> getSaleableItems();

    void selectedSaleableItem(SaleableItemDTO saleableItemDTO);

    SaleableItemDTO getSaleableItem();

    ItemType getItemType();
}
