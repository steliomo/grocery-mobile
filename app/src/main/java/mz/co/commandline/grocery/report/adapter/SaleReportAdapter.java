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
import mz.co.commandline.grocery.report.holder.ReportSaleViewHolder;
import mz.co.commandline.grocery.sale.dto.SaleReport;

public class SaleReportAdapter extends BaseAdapter<ReportSaleViewHolder> {

    private Context context;

    private List<SaleReport> sales;

    public SaleReportAdapter(Context context, List<SaleReport> sales) {
        this.context = context;
        this.sales = sales;
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
    }

    @NonNull
    @Override
    public ReportSaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sale_report, parent, false);
        ReportSaleViewHolder holder = new ReportSaleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportSaleViewHolder holder, int position) {
        SaleReport saleReport = sales.get(position);
        holder.bind(saleReport);
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }
}
