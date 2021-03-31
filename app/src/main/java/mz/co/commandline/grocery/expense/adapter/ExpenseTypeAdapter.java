package mz.co.commandline.grocery.expense.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.expense.dto.ExpenseTypeDTO;
import mz.co.commandline.grocery.expense.holder.ExpenseTypeViewHolder;
import mz.co.commandline.grocery.generics.listner.ClickListner;

public class ExpenseTypeAdapter extends BaseAdapter<ExpenseTypeViewHolder> {


    private final Context context;

    private final List<ExpenseTypeDTO> expenseTypeDTOs;

    private ClickListner listner;

    public ExpenseTypeAdapter(Context context, List<ExpenseTypeDTO> expenseTypeDTOs) {
        this.context = context;
        this.expenseTypeDTOs = expenseTypeDTOs;
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @NonNull
    @Override
    public ExpenseTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.expense_type, viewGroup, false);
        ExpenseTypeViewHolder holder = new ExpenseTypeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseTypeViewHolder expenseTypeViewHolder, int position) {
        ExpenseTypeDTO expenseTypeDTO = expenseTypeDTOs.get(position);
        expenseTypeViewHolder.setItemClickListner(listner);
        expenseTypeViewHolder.bind(expenseTypeDTO);
    }

    @Override
    public int getItemCount() {
        return expenseTypeDTOs.size();
    }
}
