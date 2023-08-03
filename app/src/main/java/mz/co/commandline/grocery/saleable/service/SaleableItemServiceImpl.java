package mz.co.commandline.grocery.saleable.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.item.dto.ProductDTO;
import mz.co.commandline.grocery.item.dto.ServiceDTO;
import mz.co.commandline.grocery.saleable.dto.ServiceItemDTO;
import mz.co.commandline.grocery.saleable.dto.StockDTO;

public class SaleableItemServiceImpl implements SaleableItemService {

    @Inject
    StockService stockService;

    @Inject
    ServiceItemService serviceItemService;

    @Inject
    public SaleableItemServiceImpl() {
    }

    @Override
    public void findSalebleItemByItemAndUnit(ItemDTO itemDTO, UnitDTO unit, final ResponseListner<List<SaleableItemDTO>> responseListner) {

        if (ItemType.PRODUCT.equals(itemDTO.getItemType())) {
            findStocks((ProductDTO) itemDTO, unit, responseListner);
            return;
        }

        findServiceItems(itemDTO, unit, responseListner);
    }

    @Override
    public void findSaleableItemsNotInThisUnitByItem(ItemDTO itemDTO, UnitDTO unit, final ResponseListner<List<SaleableItemDTO>> responseListner) {

        if (ItemType.PRODUCT.equals(itemDTO.getItemType())) {
            findStocksNotInThisUnitByProduct((ProductDTO) itemDTO, unit, responseListner);
            return;
        }

        findServiceItemsNotInThisUnitByService((ServiceDTO) itemDTO, unit, responseListner);
    }

    private void findServiceItemsNotInThisUnitByService(ServiceDTO itemDTO, UnitDTO unit, final ResponseListner<List<SaleableItemDTO>> responseListner) {
        serviceItemService.findServiceItemsNotInThisUnitByService(itemDTO, unit, new ResponseListner<List<ServiceItemDTO>>() {
            @Override
            public void success(List<ServiceItemDTO> response) {
                responseListner.success(new ArrayList<SaleableItemDTO>(response));
            }

            @Override
            public void error(String message) {
                responseListner.error(message);
            }
        });
    }

    private void findStocksNotInThisUnitByProduct(ProductDTO itemDTO, UnitDTO unit, final ResponseListner<List<SaleableItemDTO>> responseListner) {
        stockService.findProductStocksNotInThisGroceryByProduct(unit, itemDTO, new ResponseListner<List<StockDTO>>() {
            @Override
            public void success(List<StockDTO> response) {
                responseListner.success(new ArrayList<SaleableItemDTO>(response));
            }

            @Override
            public void error(String message) {
                responseListner.error(message);
            }
        });
    }

    private void findServiceItems(ItemDTO itemDTO, UnitDTO unit, final ResponseListner<List<SaleableItemDTO>> responseListner) {
        serviceItemService.findServiceItemsByServiceAndUnit((ServiceDTO) itemDTO, unit, new ResponseListner<List<ServiceItemDTO>>() {
            @Override
            public void success(List<ServiceItemDTO> response) {
                responseListner.success(new ArrayList<SaleableItemDTO>(response));
            }

            @Override
            public void error(String message) {
                responseListner.error(message);
            }
        });
    }

    private void findStocks(ProductDTO itemDTO, UnitDTO unit, final ResponseListner<List<SaleableItemDTO>> responseListner) {
        stockService.findProductStocksByGroceryAndProduct(unit, itemDTO, new ResponseListner<List<StockDTO>>() {
            @Override
            public void success(List<StockDTO> response) {
                responseListner.success(new ArrayList<SaleableItemDTO>(response));
            }

            @Override
            public void error(String message) {
                responseListner.error(message);
            }
        });
    }
}
