package mz.co.commandline.grocery.sale.delegate;

import mz.co.commandline.grocery.delegate.SaleAndStockDelegate;
import mz.co.commandline.grocery.sale.model.Sale;
import mz.co.commandline.grocery.sale.model.SaleItem;

public interface SaleDelegate extends SaleAndStockDelegate {

    void addSaleItem(SaleItem saleItem);

    Sale getSale();

    void cancel();

    void registSale();
}
