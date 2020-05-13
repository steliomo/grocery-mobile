package mz.co.commandline.grocery.expense.delegate;

import java.util.List;

import mz.co.commandline.grocery.expense.dto.ExpenseDTO;
import mz.co.commandline.grocery.expense.dto.ExpenseTypeDTO;

public interface ExpenseDelegate {

    void selectExpense();

    List<ExpenseTypeDTO> getExpenseTypes();

    void onSelectExpenseType(ExpenseTypeDTO expenseTypeDTO);

    ExpenseDTO getExpense();

    void addExpense(ExpenseDTO expenseDTO);

    List<ExpenseDTO> getExpenses();

    void registExpenses();

    void cancel();
}
