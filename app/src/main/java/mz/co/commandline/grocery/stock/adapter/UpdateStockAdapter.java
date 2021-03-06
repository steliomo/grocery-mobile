package mz.co.commandline.grocery.stock.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.stock.dto.StockDTO;
import mz.co.commandline.grocery.stock.holder.UpdateStockViewHolder;

public class UpdateStockAdapter extends BaseAdapter<UpdateStockViewHolder> {

    private Context context;
    private List<StockDTO> stocksDTO;

    public UpdateStockAdapter(Context context, List<StockDTO> stocksDTO) {
        this.context = context;
        this.stocksDTO = stocksDTO;
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
        StockDTO stockDTO = stocksDTO.get(position);
        holder.bind(stockDTO);
    }

    @Override
    public int getItemCount() {
        return stocksDTO.size();
    }
}
