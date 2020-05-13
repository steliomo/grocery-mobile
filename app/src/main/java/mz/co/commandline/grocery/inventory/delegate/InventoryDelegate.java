package mz.co.commandline.grocery.inventory.delegate;

import mz.co.commandline.grocery.inventory.dto.InventoryDTO;
import mz.co.commandline.grocery.inventory.dto.StockInventoryDTO;
import mz.co.commandline.grocery.product.delegate.ProductDelegate;

public interface InventoryDelegate extends ProductDelegate {

    void displayPerformInventoryFragment();

    void displayApproveInventoryFragment();

    InventoryDTO getInventoryDTO();

    void addStockInventoryDTO(StockInventoryDTO stockInventoryDTO);

    void cancel();

    void performInventory();

    void approveInventory();
}
