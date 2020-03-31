package mz.co.commandline.grocery.stock.service;

import java.util.List;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.product.dto.ProductDTO;
import mz.co.commandline.grocery.stock.dto.StockDTO;

public interface StockService {

    void findProductStocksByGroceryAndProduct(GroceryDTO grocery, ProductDTO productDTO, ResponseListner<List<StockDTO>> responseListner);

    void findAllStocksByGrocery(GroceryDTO groceryDTO, ResponseListner<List<StockDTO>> responseListner);

    void updateStocksAndPrices(List<StockDTO> stocksDTO, ResponseListner<Void> responseListner);
}
