package mz.co.commandline.grocery.customer.model;

import mz.co.commandline.grocery.generics.dto.GenericDTO;
import mz.co.commandline.grocery.grocery.dto.UnitDTO;

public class CustomerDTO extends GenericDTO {

    private UnitDTO unitDTO;

    private String name;

    private String address;

    private String contact;

    private String email;

    public CustomerDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(UnitDTO unitDTO) {
        this.unitDTO = unitDTO;
    }

    public UnitDTO getUnitDTO() {
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
}
