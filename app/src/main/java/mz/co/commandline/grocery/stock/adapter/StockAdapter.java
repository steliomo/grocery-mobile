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
import mz.co.commandline.grocery.stock.holder.StockViewHolder;

public class StockAdapter extends BaseAdapter<StockViewHolder> {

    private Context context;

    private List<StockDTO> stocksDTO;

    private ClickListner listner;

    public StockAdapter(Context context, List<StockDTO> stocksDTO) {
        this.context = context;
        this.stocksDTO = stocksDTO;
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
        StockDTO stockDTO = stocksDTO.get(position);
        holder.setItemClickListner(listner);
        holder.bind(stockDTO);
    }

    @Override
    public int getItemCount() {
        return stocksDTO.size();
    }
}
