package mz.co.commandline.grocery.expense.fragment;

import static mz.co.commandline.grocery.util.TextInputLayoutUtil.getInpuText;

import android.support.design.widget.TextInputLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.expense.delegate.ExpenseDelegate;
import mz.co.commandline.grocery.expense.dto.ExpenseDTO;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.validator.DateValidator;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;

public class AddExpenseFragment extends BaseFragment {

    @BindView(R.id.fragment_add_expense_type_label)
    TextView expenseType;

    @BindView(R.id.fragment_add_expense_type_description_label)
    TextView expenseTypeDecrption;

    @BindView(R.id.fragment_add_expense_performed_date)
    TextInputLayout performendDate;

    @BindView(R.id.fragment_add_expense_value)
    TextInputLayout expenseValue;

    @BindView(R.id.fragment_add_expense_description)
    TextInputLayout description;

    private ExpenseDelegate delegate;

    private ExpenseDTO expenseDTO;

    private List<Validator> validators;

    @Override
    public int getResourceId() {
        return R.layout.fragment_add_expense;
    }

    @Override
    public void onCreateView() {
        delegate = (ExpenseDelegate) getActivity();
        expenseDTO = delegate.getExpense();
        expenseType.setText(expenseDTO.getExpenseTypeDTO().getName());
        expenseTypeDecrption.setText(expenseDTO.getExpenseTypeDTO().getDescription());

        validators = new ArrayList<>();

        validators.add(new DateValidator(getActivity(), performendDate));
        validators.add(new DefaultValidator(expenseValue));
        validators.add(new DefaultValidator(description));
    }


    @OnClick(R.id.fragment_add_expense_add_btn)
    public void onClickAddBtn() {

        for (Validator validator : validators) {

            if (!validator.isValid()) {
                return;
            }
        }

        expenseDTO.setDatePerformed(getInpuText(performendDate));
        expenseDTO.setExpenseValue(getInpuText(expenseValue));
        expenseDTO.setDescription(getInpuText(description));

        delegate.addExpense(expenseDTO);
    }

    @OnClick(R.id.fragment_add_expense_cancel_btn)
    public void OnClickCancelBtn() {
        delegate.cancel();
    }

    @Override
    public String getTitle() {
        return getString(R.string.add_expense);
    }
}
