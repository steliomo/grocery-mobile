package mz.co.commandline.grocery.expense.holder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.expense.dto.ExpenseTypeDTO;
import mz.co.commandline.grocery.generics.holder.BaseViewHolder;
import mz.co.commandline.grocery.generics.listner.ClickListner;

public class ExpenseTypeViewHolder extends BaseViewHolder<ExpenseTypeDTO> {

    @BindView(R.id.expense_type_name)
    TextView expenseTypeName;

    private ExpenseTypeDTO expenseTypeDTO;
    private ClickListner listner;

    public ExpenseTypeViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(ExpenseTypeDTO expenseTypeDTO) {
        this.expenseTypeDTO = expenseTypeDTO;
        expenseTypeName.setText(expenseTypeDTO.getName());
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClickListner(expenseTypeDTO);
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
