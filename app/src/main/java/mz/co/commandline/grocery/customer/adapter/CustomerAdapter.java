package mz.co.commandline.grocery.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.customer.holder.CustomerViewHolder;
import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.generics.listner.ClickListner;

public class CustomerAdapter extends BaseAdapter<CustomerViewHolder> {


    private final Context context;

    private final List<CustomerDTO> customers;
    private ClickListner listner;

    public CustomerAdapter(Context context, List<CustomerDTO> customers) {
        this.context = context;
        this.customers = customers;
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer, parent, Boolean.FALSE);
        CustomerViewHolder holder = new CustomerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        CustomerDTO customerDTO = customers.get(position);
        holder.setItemClickListner(listner);
        holder.bind(customerDTO);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }
}
