package mz.co.commandline.grocery.payment.dto;

import java.math.BigDecimal;

import mz.co.commandline.grocery.generics.dto.EnumDTO;

public class PaymentDTO {
    private String voucher;
    private String voucherLabel;
    private String mpesaNumber;
    private BigDecimal discountValue;
    private BigDecimal total;
    private String unitUuid;

    public PaymentDTO() {
    }

    public void setVoucher(EnumDTO voucher) {
        this.voucher = voucher.getValue();
        this.voucherLabel = voucher.getLabel();
    }

    public void setMpesaNumber(String mpesaNumber) {
        this.mpesaNumber = mpesaNumber;
    }

    public String getVoucher() {
        return voucher;
    }

    public String getVoucherLabel() {
        return voucherLabel;
    }

    public String getMpesaNumber() {
        return mpesaNumber;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setUnitUuid(String unitUuid) {
        this.unitUuid = unitUuid;
    }

    public String getUnitUuid() {
        return unitUuid;
    }
}
