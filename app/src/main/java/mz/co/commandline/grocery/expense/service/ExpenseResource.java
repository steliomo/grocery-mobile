package mz.co.commandline.grocery.expense.service;


import mz.co.commandline.grocery.expense.dto.ExpensesDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ExpenseResource {

    @POST("expenses")
    Call<ExpensesDTO> registExpenses(@Body ExpensesDTO expensesDTO);

    @GET("expenses/by-unit-and-period")
    Call<ExpensesDTO> findExpensesByUnitAndPeriod(@Query("unitUuid") String unitUuid, @Query("startDate") String startDate, @Query("endDate") String endDate);
}
