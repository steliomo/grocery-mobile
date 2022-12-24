package mz.co.commandline.grocery.sale.delegate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SaleItemDTO;
import mz.co.commandline.grocery.sale.dto.SalePaymentDTO;

public interface SaleDelegate {

    void addSaleItem(SaleItemDTO saleItem);

    SaleDTO getSale();

    void registSale();

    void cancel();

    void selectItem();

    void registInstallmentSale(SaleDTO saleDTO);

    void selectedSale(SaleDTO saleDTO);

    List<SaleDTO> getPendingOrIncompleteSales();

    void payInstallment(SalePaymentDTO salePayment);
}
