package mz.co.commandline.grocery.report.holder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.Listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.holder.BaseViewHolder;
import mz.co.commandline.grocery.stock.model.Stock;

public class StockReportViewHolder extends BaseViewHolder<Stock> {

    @BindView(R.id.stock_report_product)
    TextView product;

    @BindView(R.id.stock_report_quantity)
    TextView quantity;

    public StockReportViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(Stock stock) {
        product.setText(stock.getProductDescription().getName());
        quantity.setText(stock.getQuantity().toString());
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
