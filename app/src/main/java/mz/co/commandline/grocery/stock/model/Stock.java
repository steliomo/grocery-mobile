package mz.co.commandline.grocery.stock.model;

import java.math.BigDecimal;

import mz.co.commandline.grocery.model.GenericModel;
import mz.co.commandline.grocery.product.model.ProductDescription;

public class Stock extends GenericModel {

    private ProductDescription productDescription;

    private BigDecimal salePrice;

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

}
