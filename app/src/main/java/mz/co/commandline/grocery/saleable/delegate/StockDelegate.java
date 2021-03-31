package mz.co.commandline.grocery.saleable.delegate;

import java.util.List;

import mz.co.commandline.grocery.saleable.dto.StockDTO;

public interface StockDelegate {
    List<StockDTO> getStocks();

    void selectedStock(StockDTO stockDTO);

    StockDTO getStock();
}
