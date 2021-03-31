package mz.co.commandline.grocery.item.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.item.dto.ProductDTO;
import mz.co.commandline.grocery.item.dto.ServiceDTO;

public class ItemServiceImpl implements ItemService {

    @Inject
    ProductService productService;

    @Inject
    ServiceService serviceService;

    @Inject
    public ItemServiceImpl() {
    }

    @Override
    public void findItemByUnit(ItemType itemType, GroceryDTO unit, final ResponseListner<List<ItemDTO>> responseListner) {

        if (ItemType.PRODUCT.equals(itemType)) {
            findProductsByUnit(unit, responseListner);
            return;
        }

        findServiceByUnit(unit, responseListner);
    }

    @Override
    public void findItemsNotInThisUnit(ItemType itemType, GroceryDTO unit, final ResponseListner<List<ItemDTO>> responseListner) {
        if (ItemType.PRODUCT.equals(itemType)) {
            findProductsNotInthisUnit(unit, responseListner);
            return;
        }

        findServicesNotInThisUnit(unit, responseListner);
    }

    private void findServicesNotInThisUnit(GroceryDTO unit, final ResponseListner<List<ItemDTO>> responseListner) {
        serviceService.findServicesNotInThisUnit(unit, new ResponseListner<List<ServiceDTO>>() {
            @Override
            public void success(List<ServiceDTO> response) {
                responseListner.success(new ArrayList<ItemDTO>(response));
            }

            @Override
            public void error(String message) {
                responseListner.error(message);
            }
        });
    }

    private void findProductsNotInthisUnit(GroceryDTO unit, final ResponseListner<List<ItemDTO>> responseListner) {
        productService.findProductsNotInThisUnit(unit, new ResponseListner<List<ProductDTO>>() {
            @Override
            public void success(List<ProductDTO> response) {
                responseListner.success(new ArrayList<ItemDTO>(response));
            }

            @Override
            public void error(String message) {
                responseListner.error(message);
            }
        });
    }

    private void findServiceByUnit(GroceryDTO unit, final ResponseListner<List<ItemDTO>> responseListner) {
        serviceService.findServiceByUnit(unit, new ResponseListner<List<ServiceDTO>>() {
            @Override
            public void success(List<ServiceDTO> response) {
                responseListner.success(new ArrayList<ItemDTO>(response));
            }

            @Override
            public void error(String message) {
                responseListner.error(message);
            }
        });
    }

    private void findProductsByUnit(GroceryDTO unit, final ResponseListner<List<ItemDTO>> responseListner) {
        productService.findProductsByGrocery(unit, new ResponseListner<List<ProductDTO>>() {
            @Override
            public void success(List<ProductDTO> response) {
                responseListner.success(new ArrayList<ItemDTO>(response));
            }

            @Override
            public void error(String message) {
                responseListner.error(message);
            }
        });
    }
}
