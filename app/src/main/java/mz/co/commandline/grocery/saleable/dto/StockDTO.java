package mz.co.commandline.grocery.saleable.dto;

import java.math.BigDecimal;

import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.item.dto.ProductDescriptionDTO;

public class StockDTO extends SaleableItemDTO {

    private UnitDTO unitDTO;

    private ProductDescriptionDTO productDescriptionDTO;

    private String purchasePrice;

    private String salePrice;

    private String quantity;

    private String expireDate;

    private String minimumStock;

    private int position;

    private String inventoryDate;

    private String inventoryQuantity;

    private String stockUpdateDate;

    private String stockUpdateQuantity;

    private String rentPrice;

    private String unitPerM2;

    public StockDTO() {
    }

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

    @Override
    public String getSalePrice() {
        return salePrice;
    }

    @Override
    public String getRentPrice() {
        return rentPrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public void setRentPrice(String rentPrice) {
        this.rentPrice = rentPrice;
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

    public UnitDTO getUnitDTO() {
        return unitDTO;
    }

    public void setUnitDTO(UnitDTO unitDTO) {
        this.unitDTO = unitDTO;
    }

    public String getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(String minimumStock) {
        this.minimumStock = minimumStock;
    }

    public boolean isLow() {
        return new BigDecimal(quantity).doubleValue() <= new BigDecimal(minimumStock).doubleValue();
    }

    @Override
    public ItemType getSalableItemType() {
        return ItemType.PRODUCT;
    }

    @Override
    public String getName() {
        return productDescriptionDTO.getName();
    }

    public BigDecimal discrepancy() {
        return new BigDecimal(quantity).subtract(new BigDecimal(inventoryQuantity));
    }

    public BigDecimal cashDiscrepancy() {
        return new BigDecimal(purchasePrice).multiply(discrepancy());
    }

    public String getInventoryDate() {
        return inventoryDate;
    }

    public String getInventoryQuantity() {
        return inventoryQuantity;
    }

    public String getStockUpdateDate() {
        return stockUpdateDate;
    }

    public String getStockUpdateQuantity() {
        return stockUpdateQuantity;
    }

    public BigDecimal sales() {

        if (stockUpdateQuantity == null) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(stockUpdateQuantity).subtract(new BigDecimal(quantity));
    }

    public String getUnitPerM2() {
        return unitPerM2;
    }

    public void setUnitPerM2(String unitPerM2) {
        this.unitPerM2 = unitPerM2;
    }
}
