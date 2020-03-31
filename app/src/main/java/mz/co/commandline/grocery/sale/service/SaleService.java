package mz.co.commandline.grocery.sale.service;

import java.util.List;

import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SaleReport;

public interface SaleService {

    void registSale(SaleDTO sale, ResponseListner<SaleDTO> responseListner);

    void findLast7DaysSales(String groceryUuid, ResponseListner<List<SaleReport>> responseListner);

    void findSalesPerPeriod(String groceryUuid, String startDate, String endDate, ResponseListner<List<SaleReport>> responseListner);

}
