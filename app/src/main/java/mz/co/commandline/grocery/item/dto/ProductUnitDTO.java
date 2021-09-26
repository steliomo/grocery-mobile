package mz.co.commandline.grocery.item.dto;

import java.math.BigDecimal;

public class ProductUnitDTO {

    private ProductUnitType productUnitType;

    private BigDecimal unit;

    public BigDecimal getUnit() {
        return unit;
    }

    public void setUnit(BigDecimal unit) {
        this.unit = unit;
    }

    public ProductUnitType getProductUnitType() {
        return productUnitType;
    }

    public void setProductUnitType(ProductUnitType productUnitType) {
        this.productUnitType = productUnitType;
    }
}
