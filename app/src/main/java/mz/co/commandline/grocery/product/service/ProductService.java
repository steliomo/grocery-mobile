package mz.co.commandline.grocery.product.service;

import java.util.List;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.product.dto.ProductDTO;

public interface ProductService {

    void findProductsByGrocery(GroceryDTO groceryDTO, ResponseListner<List<ProductDTO>> listner);

    void findProductsNotInThisGrocery(GroceryDTO groceryDTO, ResponseListner<List<ProductDTO>> listner);
}
