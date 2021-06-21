package mz.co.commandline.grocery.user.dto;

public class UserDTO {

    private String username;

    private String password;

    private String fullName;

    private GroceryUserDTO groceryUserDTO;

    private String email;

    private String expiryDate;

    private int numberOfUsers;

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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }
}
