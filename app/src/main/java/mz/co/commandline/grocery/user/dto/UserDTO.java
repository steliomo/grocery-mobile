package mz.co.commandline.grocery.user.dto;

import android.support.v4.view.GravityCompat;

public class UserDTO {

    private String username;

    private String password;

    private String fullName;

    private GroceryUserDTO groceryUserDTO;

    private String email;

    public UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(String fullName, String username, String password, String email) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        groceryUserDTO = new GroceryUserDTO();
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

    public GroceryUserDTO getGroceryUserDTO() {
        return groceryUserDTO;
    }

    public String getEmail() {
        return email;
    }
}
