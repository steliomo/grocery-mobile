package mz.co.commandline.grocery.product.dto;

import mz.co.commandline.grocery.model.GenericDTO;

class ProductUnitDTO extends GenericDTO {

    private ProductUnitType productUnitType;

    private String unit;

    public ProductUnitType getProductUnitType() {
        return productUnitType;
    }

    public void setProductUnitType(ProductUnitType productUnitType) {
        this.productUnitType = productUnitType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
