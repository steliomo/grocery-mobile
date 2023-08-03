package mz.co.commandline.grocery.inventory.service;

import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.inventory.dto.InventoryDTO;
import mz.co.commandline.grocery.inventory.dto.InventoryStatus;
import mz.co.commandline.grocery.generics.listner.ResponseListner;

public interface InventoryService {

    void findInventoryByGroceryAndStatus(UnitDTO grocery, InventoryStatus inventoryStatus, ResponseListner<InventoryDTO> responseListner);

    void performInventory(InventoryDTO inventory, ResponseListner<InventoryDTO> responseListner);

    void approveInventory(InventoryDTO inventory, ResponseListner<InventoryDTO> responseListner);

}
