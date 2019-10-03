package mz.co.commandline.grocery.grocery.model;

import mz.co.commandline.grocery.model.GenericModel;
import mz.co.commandline.grocery.user.model.UserRole;

public class GroceryUser extends GenericModel {

    private Grocery grocery;

    private UserRole userRole;

    private String expiryDate;

    public void setGrocery(Grocery grocery) {
        this.grocery = grocery;
    }

    public Grocery getGrocery() {
        return grocery;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(userRole.toString()).append("_").append(expiryDate);
        return builder.toString();
    }
}
