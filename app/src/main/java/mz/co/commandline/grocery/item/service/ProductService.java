package mz.co.commandline.grocery.item.service;

import java.util.List;

import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ProductDTO;

public interface ProductService {

    void findProductsByGrocery(UnitDTO groceryDTO, ResponseListner<List<ProductDTO>> listner);

    void findProductsNotInThisUnit(UnitDTO groceryDTO, ResponseListner<List<ProductDTO>> listner);
}
