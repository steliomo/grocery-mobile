package mz.co.commandline.grocery.report.delegate;

import java.math.BigDecimal;
import java.util.List;

import mz.co.commandline.grocery.sale.dto.SaleReport;
import mz.co.commandline.grocery.stock.dto.StockDTO;
import mz.co.commandline.grocery.user.dto.UserRole;

public interface ReportDelegate {

    void displayLast7DaysReport();

    void displayProductStocks();

    List<SaleReport> getSales();

    List<StockDTO> getStocks();

    BigDecimal getTotalProfit();

    BigDecimal getTotalSale();

    void displayPeriodSelectionFragment();

    void displaySalesPerPeriodReport(String startDate, String endDate);

    String getReportTitle();
}
