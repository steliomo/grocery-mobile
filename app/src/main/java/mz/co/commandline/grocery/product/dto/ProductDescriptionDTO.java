package mz.co.commandline.grocery.product.dto;

import mz.co.commandline.grocery.model.GenericDTO;

public class ProductDescriptionDTO extends GenericDTO {

    private ProductDTO productDTO;

    private String description;

    private ProductUnitDTO productUnitDTO;

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductUnitDTO getProductUnitDTO() {
        return productUnitDTO;
    }

    public void setProductUnitDTO(ProductUnitDTO productUnitDTO) {
        this.productUnitDTO = productUnitDTO;
    }

    public String getName() {

        StringBuilder builder = new StringBuilder();

        builder.append(productDTO.getName()).append(" ")
                .append(description).append(" ")
                .append(productUnitDTO.getUnit()).append(" ")
                .append(productUnitDTO.getProductUnitType());

        return builder.toString();
    }
}
