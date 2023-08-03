package mz.co.commandline.grocery.user.dto;

import mz.co.commandline.grocery.grocery.dto.UnitDTO;
import mz.co.commandline.grocery.generics.dto.GenericDTO;

public class UnitUserDTO extends GenericDTO {

    private UnitDTO unitDTO;

    private UserRole userRole;

    private String expiryDate;

    public UnitDTO getUnitDTO() {
        return unitDTO;
    }

    public void setUnitDTO(UnitDTO unitDTO) {
        this.unitDTO = unitDTO;
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
