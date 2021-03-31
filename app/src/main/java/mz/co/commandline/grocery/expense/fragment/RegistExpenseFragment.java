package mz.co.commandline.grocery.expense.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.expense.adapter.ExpenseAdapter;
import mz.co.commandline.grocery.expense.delegate.ExpenseDelegate;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;

public class RegistExpenseFragment extends BaseFragment {

    @BindView(R.id.fragment_regist_expense_recycle_view)
    RecyclerView recyclerView;

    private ExpenseDelegate expenseDelegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_regist_expense;
    }

    @Override
    public void onCreateView() {
        expenseDelegate = (ExpenseDelegate) getActivity();
        ExpenseAdapter adapter = new ExpenseAdapter(getActivity(), expenseDelegate.getExpenses());

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.fragment_regist_expense_select_expense_btn)
    public void onSelectExepnse() {
        expenseDelegate.selectExpense();
    }

    @OnClick(R.id.fragment_regist_expense_regist_btn)
    public void onClickRegistBtn() {
        expenseDelegate.registExpenses();
    }

    @Override
    public String getTitle() {
        return getString(R.string.regist_expenses);
    }
}
