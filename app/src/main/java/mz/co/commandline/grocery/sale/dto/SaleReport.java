package mz.co.commandline.grocery.sale.dto;

import java.math.BigDecimal;

import mz.co.commandline.grocery.util.DateUtil;

public class SaleReport {

    private String saleDate;

    private BigDecimal billing;

    private BigDecimal sale;

    private BigDecimal profit;

    public String getSaleDate() {
        return DateUtil.format(saleDate);
    }

    public void setBilling(BigDecimal billing) {
        this.billing = billing;
    }

    public BigDecimal getBilling() {
        return billing;
    }

    public BigDecimal getSale() {
        return sale;
    }

    public BigDecimal getProfit() {
        return profit;
    }
}
