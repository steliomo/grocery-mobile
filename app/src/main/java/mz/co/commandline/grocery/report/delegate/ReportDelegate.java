package mz.co.commandline.grocery.report.delegate;

import java.math.BigDecimal;
import java.util.List;

import mz.co.commandline.grocery.sale.model.SaleReport;
import mz.co.commandline.grocery.stock.model.Stock;

public interface ReportDelegate {

    void displayLast7DaysReport();

    void displayProductStocks();

    List<SaleReport> getSales();

    List<Stock> getStocks();

    BigDecimal getTotalProfit();

    BigDecimal getTotalSale();
}
