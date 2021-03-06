package mz.co.commandline.grocery.expense.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.expense.adapter.ExpenseTypeAdapter;
import mz.co.commandline.grocery.expense.delegate.ExpenseDelegate;
import mz.co.commandline.grocery.expense.dto.ExpenseTypeDTO;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.listner.ClickListner;

public class ExpenseTypeFragment extends BaseFragment implements ClickListner<ExpenseTypeDTO> {

    @BindView(R.id.fragment_expense_type_recycle_view)
    RecyclerView recyclerView;

    private ExpenseDelegate expenseDelegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_expense_type;
    }

    @Override
    public void onCreateView() {
        expenseDelegate = (ExpenseDelegate) getActivity();
        ExpenseTypeAdapter adapter = new ExpenseTypeAdapter(getActivity(), expenseDelegate.getExpenseTypes());
        adapter.setItemClickListner(this);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListner(ExpenseTypeDTO expenseTypeDTO) {
        expenseDelegate.onSelectExpenseType(expenseTypeDTO);
    }

    @Override
    public String getTitle() {
        return getString(R.string.expenses_type);
    }
}
