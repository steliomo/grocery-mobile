package mz.co.commandline.grocery.report.delegate;

import java.math.BigDecimal;
import java.util.List;

import mz.co.commandline.grocery.expense.dto.ExpenseDTO;
import mz.co.commandline.grocery.expense.dto.ExpenseReport;
import mz.co.commandline.grocery.sale.dto.SalesDTO;
import mz.co.commandline.grocery.saleable.dto.StockDTO;

public interface ReportDelegate {

    void displayProductOnStock();

    void displayReport(String startDate, String endDate);

    SalesDTO getSales();

    List<StockDTO> getStocks();

    void displayRecommendedProductsToAcquire();

    void onItemClick(StockDTO stockDTO);

    String getStartDate();

    String getEndDate();

    List<ExpenseReport> expensesReport();

    BigDecimal getTotalExpense();
}
