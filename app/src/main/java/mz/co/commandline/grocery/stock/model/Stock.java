package mz.co.commandline.grocery.stock.model;

import java.math.BigDecimal;

import mz.co.commandline.grocery.model.GenericModel;
import mz.co.commandline.grocery.product.model.ProductDescription;

public class Stock extends GenericModel {

    private ProductDescription productDescription;

    private BigDecimal purchasePrice;

    private BigDecimal salePrice;

    private BigDecimal quantity;

    private int position;

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public ProductDescription getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(ProductDescription productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPosition() {
        return String.valueOf(position + 1);
    }
}
