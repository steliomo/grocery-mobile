package mz.co.commandline.grocery.stock.holder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.holder.BaseViewHolder;
import mz.co.commandline.grocery.stock.model.Stock;

public class StockViewHolder extends BaseViewHolder<Stock> {

    @BindView(R.id.product_name)
    TextView productName;

    private ClickListner listner;

    private Stock stock;

    public StockViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(Stock stock) {
        this.stock = stock;
        productName.setText(stock.getProductDescription().getName());
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClickListner(stock);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
