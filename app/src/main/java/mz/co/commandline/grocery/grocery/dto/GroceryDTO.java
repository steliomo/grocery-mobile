package mz.co.commandline.grocery.grocery.dto;

import mz.co.commandline.grocery.model.GenericDTO;

public class GroceryDTO extends GenericDTO {
    
    @Override
    public String toString() {
        return getId() + "_" + getUuid();
    }
}
