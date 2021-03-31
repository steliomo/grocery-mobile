package mz.co.commandline.grocery.expense.dto;

import mz.co.commandline.grocery.generics.dto.GenericDTO;

public class ExpenseTypeDTO extends GenericDTO {

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
}
