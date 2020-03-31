package mz.co.commandline.grocery.user.dto;

public class UserDTO {

    private String username;

    private String password;

    private String fullName;

    private GroceryUserDTO groceryUserDTO;

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

    public GroceryUserDTO getGroceryUserDTO() {
        return groceryUserDTO;
    }
}
