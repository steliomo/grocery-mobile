package mz.co.commandline.grocery.expense.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.expense.dto.ExpenseDTO;
import mz.co.commandline.grocery.expense.holder.ExpenseViewHolder;
import mz.co.commandline.grocery.generics.listner.ClickListner;

public class ExpenseAdapter extends BaseAdapter<ExpenseViewHolder> {

    private final Context context;

    private final List<ExpenseDTO> expenseDTOs;

    public ExpenseAdapter(Context context, List<ExpenseDTO> expenseDTOs) {
        this.context = context;
        this.expenseDTOs = expenseDTOs;
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.expense_item, viewGroup, false);
        ExpenseViewHolder holder = new ExpenseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder expenseViewHolder, int position) {
        ExpenseDTO expenseDTO = expenseDTOs.get(position);
        expenseViewHolder.bind(expenseDTO);
    }

    @Override
    public int getItemCount() {
        return expenseDTOs.size();
    }
}
