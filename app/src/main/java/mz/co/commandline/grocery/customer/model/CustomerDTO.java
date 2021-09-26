package mz.co.commandline.grocery.customer.model;

import mz.co.commandline.grocery.generics.dto.GenericDTO;
import mz.co.commandline.grocery.grocery.dto.GroceryDTO;

public class CustomerDTO extends GenericDTO {

    private GroceryDTO unitDTO;

    private String name;

    private String address;

    private String contact;

    private String email;

    private String vehicleNumberPlate;

    public CustomerDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(GroceryDTO unitDTO) {
        this.unitDTO = unitDTO;
    }

    public GroceryDTO getUnitDTO() {
        return unitDTO;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVehicleNumberPlate() {
        return vehicleNumberPlate;
    }

    public void setVehicleNumberPlate(String vehicleNumberPlate) {
        this.vehicleNumberPlate = vehicleNumberPlate;
    }
}
