package mz.co.commandline.grocery.sale.dto;

import java.math.BigDecimal;
import java.util.List;

public class SalesDTO {

    private List<SaleReport> salesReport;

    private BigDecimal billing;

    private BigDecimal sales;

    private BigDecimal expense;

    private BigDecimal profit;

    public List<SaleReport> getSalesReport() {
        return salesReport;
    }

    public BigDecimal getBilling() {
        return billing;
    }

    public BigDecimal getSales() {
        return sales;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public BigDecimal getProfit() {
        return profit;
    }
}
