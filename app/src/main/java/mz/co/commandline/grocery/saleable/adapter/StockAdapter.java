package mz.co.commandline.grocery.saleable.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.saleable.holder.StockViewHolder;

public class StockAdapter extends BaseAdapter<StockViewHolder> {

    private Context context;

    private List<SaleableItemDTO> saleableItems;

    private ClickListner listner;

    public StockAdapter(Context context, List<SaleableItemDTO> saleableItems) {
        this.context = context;
        this.saleableItems = saleableItems;
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product, parent, false);
        StockViewHolder holder = new StockViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        SaleableItemDTO saleableItem = saleableItems.get(position);
        holder.setItemClickListner(listner);
        holder.bind(saleableItem);
    }

    @Override
    public int getItemCount() {
        return saleableItems.size();
    }
}
