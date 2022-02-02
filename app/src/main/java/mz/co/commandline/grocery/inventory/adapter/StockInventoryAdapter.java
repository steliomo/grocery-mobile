package mz.co.commandline.grocery.inventory.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.inventory.dto.StockInventoryDTO;
import mz.co.commandline.grocery.inventory.holder.StockInventoryViewHolder;
import mz.co.commandline.grocery.generics.listner.ClickListner;

public class StockInventoryAdapter extends BaseAdapter<StockInventoryViewHolder> {

    private Context context;

    private List<StockInventoryDTO> stockInventories;

    private Boolean perform;

    public StockInventoryAdapter(Context context, List<StockInventoryDTO> stockInventories, Boolean perform) {
        this.context = context;
        this.stockInventories = stockInventories;
        this.perform = perform;
    }

    @Override
    public void setItemClickListner(ClickListner listner) {

    }

    @NonNull
    @Override
    public StockInventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(perform ? R.layout.inventory_item_perform : R.layout.inventory_item, parent, false);
        StockInventoryViewHolder holder = new StockInventoryViewHolder(view, perform);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StockInventoryViewHolder holder, int position) {
        StockInventoryDTO stockInventory = stockInventories.get(position);
        holder.bind(stockInventory);
    }

    @Override
    public int getItemCount() {
        return stockInventories.size();
    }
}
