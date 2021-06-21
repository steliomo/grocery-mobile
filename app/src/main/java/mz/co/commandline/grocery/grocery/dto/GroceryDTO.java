package mz.co.commandline.grocery.grocery.dto;

import java.math.BigDecimal;

import mz.co.commandline.grocery.generics.dto.GenericDTO;

public class GroceryDTO extends GenericDTO {

    private String name;

    private String address;

    private String phoneNumber;

    private String email;

    private String unitType;

    private BigDecimal balance;

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

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return getId() + "_" + getUuid();
    }
}
