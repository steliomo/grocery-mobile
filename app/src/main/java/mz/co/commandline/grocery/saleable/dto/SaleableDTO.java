package mz.co.commandline.grocery.saleable.dto;

import java.util.ArrayList;
import java.util.List;

import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.item.dto.ItemType;

public class SaleableDTO {

    private transient List<SaleableItemDTO> saleableItems = new ArrayList<>();

    private List<StockDTO> stocks = new ArrayList<>();

    private List<ServiceItemDTO> serviceItems = new ArrayList<>();

    private UnitDTO unit;

    public void addNewSaleableItem(SaleableItemDTO saleableItem) {

        if (ItemType.PRODUCT.equals(saleableItem.getSalableItemType())) {
            StockDTO stock = (StockDTO) saleableItem;
            stock.setId(null);
            stock.setUuid(null);
            stock.setUnitDTO(unit);

            addStock(stock);
            return;
        }

        ServiceItemDTO serviceItem = (ServiceItemDTO) saleableItem;
        serviceItem.setId(null);
        serviceItem.setUuid(null);
        serviceItem.setUnitDTO(unit);

        addService(serviceItem);
    }

    public List<SaleableItemDTO> getSaleableItems() {
        return saleableItems;
    }

    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    public UnitDTO getUnit() {
        return unit;
    }

    public void updateSaleableItem(SaleableItemDTO saleableItem) {

        if (ItemType.PRODUCT.equals(saleableItem.getSalableItemType())) {
            StockDTO stock = (StockDTO) saleableItem;
            addStock(stock);
            return;
        }

        ServiceItemDTO serviceItem = (ServiceItemDTO) saleableItem;
        addService(serviceItem);
    }

    private void addStock(StockDTO stockDTO) {

        for (StockDTO stock : stocks) {
            if (stock.getName().equals(stockDTO.getName())) {
                stocks.remove(stock);
                stocks.add(stockDTO);

                saleableItems.remove(stock);
                saleableItems.add(stockDTO);
                return;
            }
        }

        stocks.add(stockDTO);
        saleableItems.add(stockDTO);
    }

    private void addService(ServiceItemDTO serviceItemDTO) {

        for (ServiceItemDTO serviceItem : serviceItems) {
            if (serviceItem.getName().equals(serviceItemDTO.getName())) {
                serviceItems.remove(serviceItem);
                serviceItems.add(serviceItemDTO);

                saleableItems.remove(serviceItem);
                saleableItems.add(serviceItemDTO);
                return;
            }
        }

        serviceItems.add(serviceItemDTO);
        saleableItems.add(serviceItemDTO);
    }
}
