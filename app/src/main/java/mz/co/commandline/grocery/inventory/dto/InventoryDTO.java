package mz.co.commandline.grocery.inventory.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.model.GenericDTO;

public class InventoryDTO extends GenericDTO {

    private GroceryDTO groceryDTO;

    private String inventoryDate;

    private InventoryStatus inventoryStatus;

    private List<StockInventoryDTO> stockInventoriesDTO;

    public InventoryDTO(GroceryDTO groceryDTO, String inventoryDate, InventoryStatus inventoryStatus) {
        this.groceryDTO = groceryDTO;
        this.inventoryDate = inventoryDate;
        this.inventoryStatus = inventoryStatus;

        stockInventoriesDTO = new ArrayList<>();
    }

    public GroceryDTO getGroceryDTO() {
        return groceryDTO;
    }

    public String getInventoryDate() {
        return inventoryDate;
    }

    public InventoryStatus getInventoryStatus() {
        return inventoryStatus;
    }

    public void addStockInventoryDTO(StockInventoryDTO stockInventoryDTO) {

        List<StockInventoryDTO> updatedStockInventoriesDTO = new ArrayList<>();

        for (StockInventoryDTO stockInventory : stockInventoriesDTO) {
            if (!stockInventory.getStockDTO().getUuid().equals(stockInventoryDTO.getStockDTO().getUuid())) {
                updatedStockInventoriesDTO.add(stockInventory);
            }
        }

        updatedStockInventoriesDTO.add(stockInventoryDTO);
        stockInventoriesDTO = new ArrayList<>(updatedStockInventoriesDTO);
    }

    public List<StockInventoryDTO> getStockInventoriesDTO() {

        Collections.sort(stockInventoriesDTO, new Comparator<StockInventoryDTO>() {
            @Override
            public int compare(StockInventoryDTO a, StockInventoryDTO b) {
                return a.getStockDTO().getProductDescriptionDTO().getName().compareTo(b.getStockDTO().getProductDescriptionDTO().getName());
            }
        });

        return Collections.unmodifiableList(stockInventoriesDTO);
    }
}
