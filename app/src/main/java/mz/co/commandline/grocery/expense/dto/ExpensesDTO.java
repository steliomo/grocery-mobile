package mz.co.commandline.grocery.expense.dto;

import java.util.ArrayList;
import java.util.List;

import mz.co.commandline.grocery.generics.dto.GenericDTO;

public class ExpensesDTO extends GenericDTO {

    private List<ExpenseTypeDTO> expenseTypeDTOs;

    private List<ExpenseDTO> expenseDTOs;

    private Long totalItems;

    public List<ExpenseTypeDTO> getExpenseTypeDTOs() {
        return expenseTypeDTOs;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public List<ExpenseDTO> getExpenseDTOs() {
        if (expenseDTOs == null) {
            expenseDTOs = new ArrayList<>();
        }
        return expenseDTOs;
    }

    public void addExpense(ExpenseDTO expenseDTO) {
        if (expenseDTOs == null) {
            expenseDTOs = new ArrayList<>();
        }

        expenseDTOs.add(expenseDTO);
    }
}
