package mz.co.commandline.grocery.stock.delegate;

import java.util.List;

import mz.co.commandline.grocery.stock.dto.StockDTO;

public interface UpdateStockDelegate {

    void updateStocksAndPrices();

    List<StockDTO> updatedStocksAndPrices();

    void cancel();

    void addStockItem(StockDTO stockDTO);
}
