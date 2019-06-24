package mz.co.commandline.grocery.report.holder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.holder.BaseViewHolder;
import mz.co.commandline.grocery.sale.model.SaleReport;
import mz.co.commandline.grocery.util.FormatterUtil;

public class ReportSaleViewHolder extends BaseViewHolder<SaleReport> {

    @BindView(R.id.sale_report_date)
    TextView saleDate;

    @BindView(R.id.sale_report_profit)
    TextView profit;

    @BindView(R.id.sale_report_sale)
    TextView sale;

    private ClickListner listner;

    private SaleReport saleReport;

    public ReportSaleViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(SaleReport saleReport) {
        this.saleReport = saleReport;
        saleDate.setText(saleReport.getSaleDate());
        profit.setText(FormatterUtil.mtFormat(saleReport.getProfit()));
        sale.setText(FormatterUtil.mtFormat(saleReport.getSale()));
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
