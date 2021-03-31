package mz.co.commandline.grocery.saleable.dto;

import java.util.ArrayList;
import java.util.List;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.item.dto.ItemType;

public class SaleableDTO {

    private transient List<SaleableItemDTO> saleableItems = new ArrayList<>();

    private List<StockDTO> stocks = new ArrayList<>();

    private List<ServiceItemDTO> serviceItems = new ArrayList<>();

    private GroceryDTO unit;

    public void addNewSaleableItem(SaleableItemDTO saleableItem) {

        if (ItemType.PRODUCT.equals(saleableItem.getSalableItemType())) {
            StockDTO stock = (StockDTO) saleableItem;
            stock.setId(null);
            stock.setUuid(null);
            stock.setGroceryDTO(unit);

            stocks.add(stock);
            saleableItems.add(stock);
            return;
        }

        ServiceItemDTO serviceItem = (ServiceItemDTO) saleableItem;
        serviceItem.setId(null);
        serviceItem.setUuid(null);
        serviceItem.setUnitDTO(unit);

        serviceItems.add(serviceItem);
        saleableItems.add(serviceItem);
    }

    public List<SaleableItemDTO> getSaleableItems() {
        return saleableItems;
    }

    public void setUnit(GroceryDTO unit) {
        this.unit = unit;
    }

    public GroceryDTO getUnit() {
        return unit;
    }

    public void updateSaleableItem(SaleableItemDTO saleableItem) {

        if (ItemType.PRODUCT.equals(saleableItem.getSalableItemType())) {
            StockDTO stock = (StockDTO) saleableItem;
            stocks.add(stock);
            saleableItems.add(stock);
            return;
        }

        ServiceItemDTO serviceItem = (ServiceItemDTO) saleableItem;

        serviceItems.add(serviceItem);
        saleableItems.add(serviceItem);
    }
}
