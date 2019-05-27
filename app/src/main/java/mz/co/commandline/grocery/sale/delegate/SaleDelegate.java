package mz.co.commandline.grocery.sale.delegate;

import java.util.List;

import mz.co.commandline.grocery.product.model.Product;
import mz.co.commandline.grocery.sale.model.Sale;
import mz.co.commandline.grocery.sale.model.SaleItem;
import mz.co.commandline.grocery.stock.model.Stock;

public interface SaleDelegate {

    void addItem();

    List<Product> getProducts();

    void selectedProduct(Product product);

    List<Stock> getStocks();

    void selectedStock(Stock stock);

    Stock getStock();

    void addSaleItem(SaleItem saleItem);

    Sale getSale();

    void cancel();

    void registSale();
}
