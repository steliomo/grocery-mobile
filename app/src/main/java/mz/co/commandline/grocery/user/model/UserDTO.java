package mz.co.commandline.grocery.user.model;

import mz.co.commandline.grocery.grocery.model.GroceryUser;

public class UserDTO {

    private String username;

    private String password;

    private String fullName;

    private GroceryUser groceryUser;

    public UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public GroceryUser getGroceryUser() {
        return groceryUser;
    }
}
