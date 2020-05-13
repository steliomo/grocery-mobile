package mz.co.commandline.grocery.expense.service;

import mz.co.commandline.grocery.expense.dto.ExpensesDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExpenseTypeResource {

    @GET("expenses-type")
    Call<ExpensesDTO> findAllExpensesType(@Query("currentPage") int currentpage, @Query("maxResult") int maxResult);
}
