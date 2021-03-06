package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.expense.delegate.ExpenseDelegate;
import mz.co.commandline.grocery.expense.dto.ExpenseDTO;
import mz.co.commandline.grocery.expense.dto.ExpenseTypeDTO;
import mz.co.commandline.grocery.expense.dto.ExpensesDTO;
import mz.co.commandline.grocery.expense.fragment.AddExpenseFragment;
import mz.co.commandline.grocery.expense.fragment.ExpenseTypeFragment;
import mz.co.commandline.grocery.expense.fragment.RegistExpenseFragment;
import mz.co.commandline.grocery.expense.service.ExpenseService;
import mz.co.commandline.grocery.expense.service.ExpenseTypeService;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

import static mz.co.commandline.grocery.util.FragmentUtil.displayFragment;
import static mz.co.commandline.grocery.util.FragmentUtil.popBackStack;
import static mz.co.commandline.grocery.util.FragmentUtil.resetFragment;

public class ExpenseActivity extends BaseAuthActivity implements View.OnClickListener, ExpenseDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ExpenseTypeService expenseTypeService;

    @Inject
    ExpenseService expenseService;

    @Inject
    UserService userService;

    private FragmentManager fragmentManager;

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

        fragmentManager = getSupportFragmentManager();
        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        dialogManager = new AlertDialogManager(this);

        displayFragment(fragmentManager, R.id.expense_activity_framelayout, new RegistExpenseFragment(), Boolean.FALSE);

        expensesDTO = new ExpensesDTO();
    }


    @Override
    public void onClick(View view) {
        popBackStack(fragmentManager, this);
    }

    @Override
    public void selectExpense() {
        progressBar.show();

        expenseTypeService.findAllExpenseTypes(0, 10, new ResponseListner<ExpensesDTO>() {
            @Override
            public void success(ExpensesDTO response) {
                progressBar.dismiss();
                expenseTypeDTOS = new ArrayList<>(response.getExpenseTypeDTOs());
                displayFragment(fragmentManager, R.id.expense_activity_framelayout, new ExpenseTypeFragment(), Boolean.TRUE);
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
        this.expenseDTO.setGroceryDTO(userService.getGroceryDTO());
        this.expenseDTO.setExpenseTypeDTO(expenseTypeDTO);
        displayFragment(fragmentManager, R.id.expense_activity_framelayout, new AddExpenseFragment(), Boolean.TRUE);
    }

    @Override
    public ExpenseDTO getExpense() {
        return expenseDTO;
    }

    @Override
    public void addExpense(ExpenseDTO expenseDTO) {
        expensesDTO.addExpense(expenseDTO);
        resetFragment(fragmentManager);
        displayFragment(fragmentManager, R.id.expense_activity_framelayout, new RegistExpenseFragment(), Boolean.FALSE);
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

                if (message.contains("Please add at least one expense to be registed!")) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.no_expenses_to_be_registered), null);
                    return;
                }

                dialogManager.dialog(AlertType.ERROR, getString(R.string.expense_regist_error), null);
                Log.e("EXPENSES", message);
            }
        });

    }

    @Override
    public void cancel() {
        resetFragment(fragmentManager);
    }
}
