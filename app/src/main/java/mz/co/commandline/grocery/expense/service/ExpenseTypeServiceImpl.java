package mz.co.commandline.grocery.expense.service;

import javax.inject.Inject;

import mz.co.commandline.grocery.expense.dto.ExpensesDTO;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseTypeServiceImpl extends AbstractService implements ExpenseTypeService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public ExpenseTypeServiceImpl() {
    }

    @Override
    public void findAllExpenseTypes(int currentPage, int maxResult, final ResponseListner<ExpensesDTO> listner) {
        getResource().findAllExpensesType(currentPage, maxResult).enqueue(new Callback<ExpensesDTO>() {
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

    @Override
    public ExpenseTypeResource getResource() {
        return retrofitService.getResource(ExpenseTypeResource.class);
    }
}
