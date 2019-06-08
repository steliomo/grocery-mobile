package mz.co.commandline.grocery.stock.delegate;

import java.util.List;

import mz.co.commandline.grocery.delegate.SaleAndStockDelegate;
import mz.co.commandline.grocery.stock.model.Stock;

public interface StockDelegate extends SaleAndStockDelegate {

    void addStockItem(Stock stock);

    void updateStocksAndPrices();

    List<Stock> updatedStocksAndPrices();
}
