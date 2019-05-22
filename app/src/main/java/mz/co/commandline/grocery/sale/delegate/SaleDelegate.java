package mz.co.commandline.grocery.sale.delegate;

import java.util.List;

import mz.co.commandline.grocery.product.model.Product;
import mz.co.commandline.grocery.stock.model.Stock;

public interface SaleDelegate {

    void addItem();

    List<Product> getProducts();

    void selectedProduct(Product product);

    List<Stock> getStocks();
}
