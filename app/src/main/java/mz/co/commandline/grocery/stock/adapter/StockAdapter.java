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
import mz.co.commandline.grocery.stock.holder.StockViewHolder;
import mz.co.commandline.grocery.stock.model.Stock;

public class StockAdapter extends BaseAdapter<StockViewHolder> {

    private Context context;

    private List<Stock> stocks;

    private ClickListner listner;

    public StockAdapter(Context context, List<Stock> stocks) {
        this.context = context;
        this.stocks = stocks;
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
        Stock stock = stocks.get(position);
        holder.setItemClickListner(listner);
        holder.bind(stock);
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }
}
