package mz.co.commandline.grocery.inventory.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.generics.dto.GenericDTO;

public class InventoryDTO extends GenericDTO {

    private UnitDTO unitDTO;

    private String inventoryDate;

    private InventoryStatus inventoryStatus;

    private List<StockInventoryDTO> stockInventoriesDTO;

    public InventoryDTO(UnitDTO groceryDTO, String inventoryDate, InventoryStatus inventoryStatus) {
        this.unitDTO = groceryDTO;
        this.inventoryDate = inventoryDate;
        this.inventoryStatus = inventoryStatus;

        stockInventoriesDTO = new ArrayList<>();
    }

    public UnitDTO getUnitDTO() {
        return unitDTO;
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
