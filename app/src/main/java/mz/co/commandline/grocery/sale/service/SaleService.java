package mz.co.commandline.grocery.sale.service;

import java.util.List;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.pos.dto.DebtDTO;
import mz.co.commandline.grocery.pos.dto.DebtItemDTO;
import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SalePaymentDTO;
import mz.co.commandline.grocery.sale.dto.SalesDTO;

public interface SaleService {

    void registSale(SaleDTO sale, ResponseListner<SaleDTO> responseListner);

    void findSalesPerPeriod(String groceryUuid, String startDate, String endDate, ResponseListner<SalesDTO> responseListner);

    void findMonthlyalesPerPeriod(String groceryUuid, String startDate, String endDate, ResponseListner<SalesDTO> responseListner);

    void findPendingOrIncompleteSalesByCustomer(String customerUuid, ResponseListner<SalesDTO> responseListner);

    void salePayment(SalePaymentDTO salePaymentDTO, ResponseListner<SalePaymentDTO> responseListner);

    void fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(String customerUuid, ResponseListner<SalesDTO> responseListner);

    void fetchSalesWithDeliveryGuidesByCustomer(String customerUuid, ResponseListner<SalesDTO> responseListner);

    void processOpenTable(SaleDTO table, ResponseListner<SaleDTO> responseListner);

    void fetchOpenedTables(String unitUuid, ResponseListner<SalesDTO> responseListner);

    void registAddedItems(SaleDTO table, ResponseListner<SaleDTO> responseListner);

    void fetchOpenedTableByUuid(String tableUuid, ResponseListner<SaleDTO> responseListner);

    void sendTableBill(SaleDTO table, ResponseListner<SaleDTO> responseListner);

    void cancelTable(SaleDTO table, ResponseListner<SaleDTO> responseListner);

    void registCreditSale(String saleUuid, ResponseListner<SaleDTO> responseListner);

    void payDebt(DebtDTO bill, ResponseListner<DebtDTO> responseListner);

    void findDebtByCustomer(String customerUuid, ResponseListner<DebtDTO> responseListner);

    void findDebtItemsbByCustomer(String customerUuid, ResponseListner<List<DebtItemDTO>> responseListner);
    void sendCustomerDebt(DebtDTO debt, ResponseListner<Void> voidResponseListner);
}
