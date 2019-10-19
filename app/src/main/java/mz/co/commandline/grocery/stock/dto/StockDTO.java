package mz.co.commandline.grocery.stock.dto;

import mz.co.commandline.grocery.model.GenericDTO;
import mz.co.commandline.grocery.product.dto.ProductDescriptionDTO;

public class StockDTO extends GenericDTO {

    private ProductDescriptionDTO productDescriptionDTO;

    private String purchasePrice;

    private String salePrice;

    private String quantity;

    private String expireDate;

    private int position;

    public ProductDescriptionDTO getProductDescriptionDTO() {
        return productDescriptionDTO;
    }

    public void setProductDescriptionDTO(ProductDescriptionDTO productDescriptionDTO) {
        this.productDescriptionDTO = productDescriptionDTO;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPosition() {
        return String.valueOf(position + 1);
    }
}
