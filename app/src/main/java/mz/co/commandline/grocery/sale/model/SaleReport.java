package mz.co.commandline.grocery.sale.model;

import java.math.BigDecimal;

import mz.co.commandline.grocery.util.DateUtil;

public class SaleReport {

    private String saleDate;

    private BigDecimal profit;

    private BigDecimal sale;

    public String getSaleDate() {
        return DateUtil.format(saleDate);
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public BigDecimal getSale() {
        return sale;
    }

}
