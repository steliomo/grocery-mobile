package mz.co.commandline.grocery.sale.service;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SalePaymentDTO;
import mz.co.commandline.grocery.sale.dto.SalesDTO;

public interface SaleService {

    void registSale(SaleDTO sale, ResponseListner<SaleDTO> responseListner);

    void findSalesPerPeriod(String groceryUuid, String startDate, String endDate, ResponseListner<SalesDTO> responseListner);

    void findMonthlyalesPerPeriod(String groceryUuid, String startDate, String endDate, ResponseListner<SalesDTO> responseListner);

    void findPendingOrIncompleteSalesByCustomer(String customerUuid, ResponseListner<SalesDTO> responseListner);

    void salePayment(SalePaymentDTO salePaymentDTO, ResponseListner<SalePaymentDTO> responseListner);
}
