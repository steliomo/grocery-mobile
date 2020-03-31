package mz.co.commandline.grocery.user.dto;

import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.model.GenericDTO;

public class GroceryUserDTO extends GenericDTO {

    private GroceryDTO groceryDTO;

    private UserRole userRole;

    private String expiryDate;

    public GroceryDTO getGroceryDTO() {
        return groceryDTO;
    }

    public void setGroceryDTO(GroceryDTO groceryDTO) {
        this.groceryDTO = groceryDTO;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(userRole.toString()).append("_").append(expiryDate);
        return builder.toString();
    }
}
