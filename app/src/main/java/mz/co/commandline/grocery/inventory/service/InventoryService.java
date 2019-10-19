package mz.co.commandline.grocery.inventory.service;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.inventory.dto.InventoryDTO;
import mz.co.commandline.grocery.inventory.dto.InventoryStatus;
import mz.co.commandline.grocery.listner.ResponseListner;

public interface InventoryService {

    void findInventoryByGroceryAndStatus(GroceryDTO grocery, InventoryStatus inventoryStatus, ResponseListner<InventoryDTO> responseListner);

    void performInventory(InventoryDTO inventory, ResponseListner<InventoryDTO> responseListner);

    void approveInventory(InventoryDTO inventory, ResponseListner<InventoryDTO> responseListner);

}
