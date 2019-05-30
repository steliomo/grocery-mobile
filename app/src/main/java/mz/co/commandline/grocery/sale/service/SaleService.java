package mz.co.commandline.grocery.sale.service;

import java.util.List;

import mz.co.commandline.grocery.Listner.ResponseListner;
import mz.co.commandline.grocery.sale.model.Sale;
import mz.co.commandline.grocery.sale.model.SaleReport;

public interface SaleService {

    void registSale(Sale sale, ResponseListner<Sale> responseListner);

    void findLast7DaysSales(ResponseListner<List<SaleReport>> responseListner);

}
