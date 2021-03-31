package mz.co.commandline.grocery.item.dto;

import mz.co.commandline.grocery.generics.dto.GenericDTO;

public class ServiceDescriptionDTO extends GenericDTO {

    private ServiceDTO serviceDTO;

    private String description;

    public ServiceDTO getServiceDTO() {
        return serviceDTO;
    }

    public String getDescription() {
        return description;
    }
}
