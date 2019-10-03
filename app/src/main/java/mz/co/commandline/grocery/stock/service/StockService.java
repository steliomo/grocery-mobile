package mz.co.commandline.grocery.stock.service;

import java.util.List;

import mz.co.commandline.grocery.grocery.model.Grocery;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.product.model.Product;
import mz.co.commandline.grocery.stock.model.Stock;

public interface StockService {

    void findProductStocksByGroceryAndProduct(Grocery grocery, Product product, ResponseListner<List<Stock>> responseListner);

    void findAllStocksByGrocery(Grocery grocery, ResponseListner<List<Stock>> responseListner);

    void updateStocksAndPrices(List<Stock> stocks, ResponseListner<Void> responseListner);
}
