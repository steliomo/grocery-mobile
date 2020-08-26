package mz.co.commandline.grocery.grocery.dto;

import mz.co.commandline.grocery.dto.GenericDTO;

public class GroceryDTO extends GenericDTO {

    private String name;

    private String address;

    private String phoneNumber;

    private String email;

    public GroceryDTO() {
    }

    public GroceryDTO(String name, String address, String phoneNumber, String email) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return getId() + "_" + getUuid();
    }
}
