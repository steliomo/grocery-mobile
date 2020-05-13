package mz.co.commandline.grocery.product.dto;

import mz.co.commandline.grocery.dto.GenericDTO;

public class ProductDTO extends GenericDTO {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
