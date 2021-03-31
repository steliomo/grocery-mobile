package mz.co.commandline.grocery.item.service;

import java.util.List;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ProductDTO;

public interface ProductService {

    void findProductsByGrocery(GroceryDTO groceryDTO, ResponseListner<List<ProductDTO>> listner);

    void findProductsNotInThisUnit(GroceryDTO groceryDTO, ResponseListner<List<ProductDTO>> listner);
}
