package mz.co.commandline.grocery.expense.holder;

import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.expense.dto.ExpenseDTO;
import mz.co.commandline.grocery.generics.holder.BaseViewHolder;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.util.FormatterUtil;

public class ExpenseViewHolder extends BaseViewHolder<ExpenseDTO> {

    @BindView(R.id.expense_item_name)
    TextView name;

    @BindView(R.id.expense_item_date)
    TextView date;

    @BindView(R.id.expense_item_value)
    TextView value;

    public ExpenseViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(ExpenseDTO expenseDTO) {
        name.setText(expenseDTO.getName());
        date.setText(expenseDTO.getDatePerformed());
        value.setText(FormatterUtil.mtFormat(new BigDecimal(expenseDTO.getExpenseValue())));
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
