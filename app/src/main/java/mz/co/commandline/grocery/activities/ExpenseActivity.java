package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.expense.delegate.ExpenseDelegate;
import mz.co.commandline.grocery.expense.dto.ExpenseDTO;
import mz.co.commandline.grocery.expense.dto.ExpenseTypeDTO;
import mz.co.commandline.grocery.expense.dto.ExpensesDTO;
import mz.co.commandline.grocery.expense.fragment.AddExpenseFragment;
import mz.co.commandline.grocery.expense.fragment.ExpenseTypeFragment;
import mz.co.commandline.grocery.expense.fragment.RegistExpenseFragment;
import mz.co.commandline.grocery.expense.service.ExpenseService;
import mz.co.commandline.grocery.expense.service.ExpenseTypeService;
import mz.co.commandline.grocery.generics.dto.ErrorMessage;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

public class ExpenseActivity extends BaseAuthActivity implements View.OnClickListener, ExpenseDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ExpenseTypeService expenseTypeService;

    @Inject
    ExpenseService expenseService;

    @Inject
    UserService userService;

    private ProgressDialog progressBar;

    private AlertDialogManager dialogManager;

    private List<ExpenseTypeDTO> expenseTypeDTOS;

    private ExpenseDTO expenseDTO;

    private ExpensesDTO expensesDTO;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_expense);

        toolbar.setTitle(R.string.expenses);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        GroceryComponent component = application.getComponent();
        component.inject(this);


        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        dialogManager = new AlertDialogManager(this);

        showFragment(new RegistExpenseFragment(), Boolean.FALSE);

        expensesDTO = new ExpensesDTO();
    }


    @Override
    public void onClick(View view) {
        popBackStack();
    }

    @Override
    public void selectExpense() {
        progressBar.show();

        expenseTypeService.findAllExpenseTypes(0, 10, new ResponseListner<ExpensesDTO>() {
            @Override
            public void success(ExpensesDTO response) {
                progressBar.dismiss();
                expenseTypeDTOS = new ArrayList<>(response.getExpenseTypeDTOs());
                showFragment(new ExpenseTypeFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_fetching_expenses_type), null);
                Log.e("EXPENSES TYPE", message);
            }
        });
    }

    @Override
    public List<ExpenseTypeDTO> getExpenseTypes() {
        return expenseTypeDTOS;
    }

    @Override
    public void onSelectExpenseType(ExpenseTypeDTO expenseTypeDTO) {
        this.expenseDTO = new ExpenseDTO();
        this.expenseDTO.setUnitDTO(userService.getUnitDTO());
        this.expenseDTO.setExpenseTypeDTO(expenseTypeDTO);
        showFragment(new AddExpenseFragment(), Boolean.TRUE);
    }

    @Override
    public ExpenseDTO getExpense() {
        return expenseDTO;
    }

    @Override
    public void addExpense(ExpenseDTO expenseDTO) {
        expensesDTO.addExpense(expenseDTO);
        resetFragment();
        showFragment(new RegistExpenseFragment(), Boolean.FALSE);
    }

    @Override
    public List<ExpenseDTO> getExpenses() {
        return Collections.unmodifiableList(expensesDTO.getExpenseDTOs());
    }

    @Override
    public void registExpenses() {
        progressBar.show();

        expenseService.registExpenses(expensesDTO, new ResponseListner<ExpensesDTO>() {
            @Override
            public void success(ExpensesDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.expenses_registed_success), new AlertListner() {
                    @Override
                    public void perform() {
                        finish();
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.expense_regist_error), null);
                Log.e("EXPENSES", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("EXPENSES_B", errorMessage.getDeveloperMessage());
            }
        });
    }

    @Override
    public void cancel() {
        resetFragment();
    }

    @Override
    public int getActivityFrameLayoutId() {
        return R.id.expense_activity_framelayout;
    }
}
