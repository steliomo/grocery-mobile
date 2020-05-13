package mz.co.commandline.grocery.expense.service;

import java.util.List;

import mz.co.commandline.grocery.expense.dto.ExpensesDTO;
import mz.co.commandline.grocery.listner.ResponseListner;

public interface ExpenseTypeService {
    void findAllExpenseTypes(int currentPage, int maxResult, ResponseListner<ExpensesDTO> listner);
}
