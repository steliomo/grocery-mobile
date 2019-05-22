package mz.co.commandline.grocery.product.model;

import java.math.BigDecimal;

public class ProductUnit {

    private ProductUnitType productUnitType;

    private BigDecimal unit;

    public ProductUnitType getProductUnitType() {
        return productUnitType;
    }

    public void setProductUnitType(ProductUnitType productUnitType) {
        this.productUnitType = productUnitType;
    }

    public BigDecimal getUnit() {
        return unit;
    }

    public void setUnit(BigDecimal unit) {
        this.unit = unit;
    }

    public String getProductUnit() {
        return unit + " " + productUnitType;
    }
}
