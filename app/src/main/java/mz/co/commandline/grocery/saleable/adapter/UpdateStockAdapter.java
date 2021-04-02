package mz.co.commandline.grocery.saleable.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.saleable.holder.UpdateStockViewHolder;

public class UpdateStockAdapter extends BaseAdapter<UpdateStockViewHolder> {

    private Context context;
    private List<SaleableItemDTO> saleableItems;

    public UpdateStockAdapter(Context context, List<SaleableItemDTO> saleableItems) {
        this.context = context;
        this.saleableItems = saleableItems;
    }

    @Override
    public void setItemClickListner(ClickListner listner) {

    }

    @NonNull
    @Override
    public UpdateStockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stock_item, parent, false);
        UpdateStockViewHolder holder = new UpdateStockViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateStockViewHolder holder, int position) {
        SaleableItemDTO saleableItem = saleableItems.get(position);
        holder.bind(saleableItem);
    }

    @Override
    public int getItemCount() {
        return saleableItems.size();
    }
}
