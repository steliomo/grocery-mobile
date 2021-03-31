package mz.co.commandline.grocery.item.service;

import java.util.List;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ItemType;

public interface ItemService {

    void findItemByUnit(ItemType itemType, GroceryDTO unit, ResponseListner<List<ItemDTO>> responseListner);

    void findItemsNotInThisUnit(ItemType itemType, GroceryDTO unit, ResponseListner<List<ItemDTO>> responseListner);
}
