package mz.co.commandline.grocery.item.delegate;

import java.util.List;

import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.item.dto.ItemType;

public interface ItemDelegate {

    void selectItemType(ItemType itemType);

    List<ItemDTO> getItems();

    void selectedItem(ItemDTO itemDTO);

    ItemType getItemType();
}
