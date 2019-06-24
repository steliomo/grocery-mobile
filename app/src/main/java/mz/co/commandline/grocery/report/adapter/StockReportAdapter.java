package mz.co.commandline.grocery.report.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.report.holder.StockReportViewHolder;
import mz.co.commandline.grocery.stock.model.Stock;

public class StockReportAdapter extends BaseAdapter<StockReportViewHolder> {

    private final Context context;
    private final List<Stock> stocks;

    public StockReportAdapter(Context context, List<Stock> stocks) {
        this.context = context;
        this.stocks = stocks;
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
    }

    @NonNull
    @Override
    public StockReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stock_report, parent, false);
        StockReportViewHolder holder = new StockReportViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StockReportViewHolder holder, int position) {
        Stock stock = stocks.get(position);
        holder.bind(stock);
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }
}
