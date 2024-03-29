package mz.co.commandline.grocery.saleable.service;

import java.util.List;

import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ProductDTO;
import mz.co.commandline.grocery.saleable.dto.StockDTO;

public interface StockService {

    void findProductStocksByGroceryAndProduct(UnitDTO grocery, ProductDTO productDTO, ResponseListner<List<StockDTO>> responseListner);

    void findAllStocksByGrocery(UnitDTO groceryDTO, ResponseListner<List<StockDTO>> responseListner);

    void findLowStocksByGroceryAndSalePeriod(UnitDTO groceryDTO, String startDate, String endDate, ResponseListner<List<StockDTO>> responseListner);

    void findProductStocksNotInThisGroceryByProduct(UnitDTO grocery, ProductDTO productDTO, ResponseListner<List<StockDTO>> responseListner);

    void findStocksInAnalysis(String unitUuid, ResponseListner<List<StockDTO>> responseListner);

    void regularizeStock(StockDTO stockDTO, ResponseListner<StockDTO> responseListner);
}
