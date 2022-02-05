package mz.co.commandline.grocery.inventory.delegate;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mz.co.commandline.grocery.inventory.dto.InventoryDTO;
import mz.co.commandline.grocery.inventory.dto.StockInventoryDTO;
import mz.co.commandline.grocery.saleable.delegate.SaleableItemDelegate;
import mz.co.commandline.grocery.saleable.dto.StockDTO;

public interface InventoryDelegate extends SaleableItemDelegate {

    InventoryDTO getInventoryDTO();

    void addStockInventoryDTO(StockInventoryDTO stockInventoryDTO);

    void cancel();

    void performInventory();

    void approveInventory();

    List<StockDTO> getStocksDTO();

    void stockAnalysisDtails(@Nullable StockDTO stockDTO);

    void regularizeStock(@NotNull StockDTO stockDTO);
}
