package mz.co.commandline.grocery.sale.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.sale.holder.SaleItemViewHolder;
import mz.co.commandline.grocery.sale.model.SaleItem;

public class SaleItemAdapter extends BaseAdapter<SaleItemViewHolder> {

    private final Context context;

    private final List<SaleItem> saleItems;

    private ClickListner listner;

    public SaleItemAdapter(Context context, List<SaleItem> saleItems) {
        this.context = context;
        this.saleItems = saleItems;
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @NonNull
    @Override
    public SaleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sale_item, parent, false);
        SaleItemViewHolder holder = new SaleItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SaleItemViewHolder holder, int position) {
        SaleItem saleItem = saleItems.get(position);
        holder.setItemClickListner(listner);
        holder.bind(saleItem);
    }

    @Override
    public int getItemCount() {
        return saleItems.size();
    }
}
