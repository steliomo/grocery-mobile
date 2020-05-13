package mz.co.commandline.grocery.report.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.report.holder.StockReportViewHolder;
import mz.co.commandline.grocery.stock.dto.StockDTO;

public class StockReportAdapter extends BaseAdapter<StockReportViewHolder> implements Filterable {

    private final Context context;

    private final List<StockDTO> stocksDTO;

    private ClickListner listner;

    private Filter filter;

    public StockReportAdapter(Context context, final List<StockDTO> stocks) {
        this.context = context;
        this.stocksDTO = stocks;
        final List<StockDTO> stockDTOS = new ArrayList<>(stocks);

        filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<StockDTO> filteredStocks = new ArrayList<>();
                FilterResults results = new FilterResults();

                if (charSequence.length() != 0) {
                    for (StockDTO stock : stocksDTO) {
                        if (stock.getProductDescriptionDTO().getName().toLowerCase().contains(charSequence.toString().toLowerCase().trim())) {
                            filteredStocks.add(stock);
                        }
                    }

                    results.values = filteredStocks;
                    return results;
                }

                results.values = stockDTOS;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                stocks.clear();
                stocks.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
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
        StockDTO stockDTO = stocksDTO.get(position);
        stockDTO.setPosition(position);
        holder.setItemClickListner(listner);
        holder.bind(stockDTO);
    }

    @Override
    public int getItemCount() {
        return stocksDTO.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
}
