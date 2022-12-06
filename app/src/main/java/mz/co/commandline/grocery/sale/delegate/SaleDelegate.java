package mz.co.commandline.grocery.sale.delegate;

import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SaleItemDTO;

public interface SaleDelegate {

    void addSaleItem(SaleItemDTO saleItem);

    SaleDTO getSale();

    void registSale();

    void cancel();

    void selectItem();

    void registInstallmentSale(SaleDTO saleDTO);
}
