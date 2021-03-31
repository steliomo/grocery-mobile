package mz.co.commandline.grocery.saleable.delegate;

import java.util.List;

import mz.co.commandline.grocery.saleable.dto.StockDTO;

public interface UpdateStockDelegate {

    void updateStocksAndPrices();

    List<StockDTO> updatedStocksAndPrices();

    void cancel();

    void selectItem();

    void addStockItem(StockDTO stockDTO);
}
