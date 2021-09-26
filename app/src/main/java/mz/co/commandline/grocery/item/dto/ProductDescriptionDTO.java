package mz.co.commandline.grocery.item.dto;

import mz.co.commandline.grocery.generics.dto.GenericDTO;

public class ProductDescriptionDTO extends GenericDTO {

    private ProductDTO productDTO;

    private String description;

    private ProductUnitDTO productUnitDTO;

    private String name;

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
