package mz.co.commandline.grocery.stock.service;

import java.util.List;

import mz.co.commandline.grocery.Listner.ResponseListner;
import mz.co.commandline.grocery.product.model.Product;
import mz.co.commandline.grocery.stock.model.Stock;

public interface StockService {

    void findProductStocksByProduct(Product product, ResponseListner<List<Stock>> responseListner);

    void findAllStocks(ResponseListner<List<Stock>> responseListner);
}
