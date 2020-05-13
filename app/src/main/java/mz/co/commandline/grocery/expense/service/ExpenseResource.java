package mz.co.commandline.grocery.expense.service;


import mz.co.commandline.grocery.expense.dto.ExpensesDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ExpenseResource {

    @POST("expenses")
    Call<ExpensesDTO> registExpenses(@Body ExpensesDTO expensesDTO);
}
