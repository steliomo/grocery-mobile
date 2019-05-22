package mz.co.commandline.grocery.product.model;

public class ProductDescription {

    private Product product;

    private ProductUnit productUnit;

    private String description;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductUnit getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(ProductUnit productUnit) {
        this.productUnit = productUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return product.getName() + " " + description + " " + productUnit.getProductUnit();
    }
}
