package mz.co.commandline.grocery.delegate;

import java.util.List;

import mz.co.commandline.grocery.product.model.Product;
import mz.co.commandline.grocery.stock.model.Stock;

public interface SaleAndStockDelegate {

    void addItem();

    List<Product> getProducts();

    void selectedProduct(Product product);

    List<Stock> getStocks();

    void selectedStock(Stock stock);

    Stock getStock();

    void cancel();
}
