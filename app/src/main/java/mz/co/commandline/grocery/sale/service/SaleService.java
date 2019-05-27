package mz.co.commandline.grocery.sale.service;

import mz.co.commandline.grocery.Listner.ResponseListner;
import mz.co.commandline.grocery.sale.model.Sale;

public interface SaleService {

    void registSale(Sale sale, ResponseListner<Sale> responseListner);

}
