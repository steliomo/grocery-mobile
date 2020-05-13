package mz.co.commandline.grocery.expense.dto;

import mz.co.commandline.grocery.dto.GenericDTO;
import mz.co.commandline.grocery.grocery.dto.GroceryDTO;

public class ExpenseDTO extends GenericDTO {

    private GroceryDTO groceryDTO;

    private ExpenseTypeDTO expenseTypeDTO;

    private String datePerformed;

    private String expenseValue;

    private String description;

    public GroceryDTO getGroceryDTO() {
        return groceryDTO;
    }

    public void setGroceryDTO(GroceryDTO groceryDTO) {
        this.groceryDTO = groceryDTO;
    }

    public ExpenseTypeDTO getExpenseTypeDTO() {
        return expenseTypeDTO;
    }

    public void setExpenseTypeDTO(ExpenseTypeDTO expenseTypeDTO) {
        this.expenseTypeDTO = expenseTypeDTO;
    }

    public String getDatePerformed() {
        return datePerformed;
    }

    public void setDatePerformed(String datePerformed) {
        this.datePerformed = datePerformed;
    }

    public String getExpenseValue() {
        return expenseValue;
    }

    public void setExpenseValue(String expenseValue) {
        this.expenseValue = expenseValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return expenseTypeDTO.getName();
    }
}
