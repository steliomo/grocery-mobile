package mz.co.commandline.grocery.expense.service;

import mz.co.commandline.grocery.expense.dto.ExpensesDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;

public interface ExpenseService {
    void registExpenses(ExpensesDTO expensesDTO, ResponseListner<ExpensesDTO> expensesDTOResponseListner);

    void findExpensesByUnitAndPeriod(String unitUuid, String startDate, String endDate, ResponseListner<ExpensesDTO> expensesDTOResponseListner);
}
