package mz.co.commandline.grocery.expense.service;

import java.io.IOException;

import javax.inject.Inject;

import mz.co.commandline.grocery.expense.dto.ExpensesDTO;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.service.AbstractService;
import mz.co.commandline.grocery.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseServiceImpl extends AbstractService implements ExpenseService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public ExpenseServiceImpl() {
    }

    @Override
    public void registExpenses(ExpensesDTO expensesDTO, final ResponseListner<ExpensesDTO> listner) {
        getResource().registExpenses(expensesDTO).enqueue(new Callback<ExpensesDTO>() {
            @Override
            public void onResponse(Call<ExpensesDTO> call, Response<ExpensesDTO> response) {
                if (response.isSuccessful()) {
                    listner.success(response.body());
                    return;
                }

                setBodyError(response, listner);
            }

            @Override
            public void onFailure(Call<ExpensesDTO> call, Throwable t) {
                listner.error(t.getMessage());
            }
        });
    }

    private ExpenseResource getResource() {
        return retrofitService.getResource(ExpenseResource.class);
    }
}
