package mz.co.commandline.grocery.saleable.service;

import java.util.List;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;

public interface SaleableItemService {

    void findSalebleItemByItemAndUnit(ItemDTO itemDTO, GroceryDTO unit, ResponseListner<List<SaleableItemDTO>> responseListner);

    void findSaleableItemsNotInThisUnitByItem(ItemDTO itemDTO, GroceryDTO unit, ResponseListner<List<SaleableItemDTO>> responseListner);
}
