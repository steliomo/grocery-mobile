package mz.co.commandline.grocery.stock.delegate;

import java.util.List;

import mz.co.commandline.grocery.stock.dto.StockDTO;

public interface StockDelegate {
    List<StockDTO> getStocks();

    void selectedStock(StockDTO stockDTO);

    StockDTO getStock();
}
