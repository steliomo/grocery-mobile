package mz.co.commandline.grocery.stock.holder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.holder.BaseViewHolder;
import mz.co.commandline.grocery.stock.model.Stock;
import mz.co.commandline.grocery.util.FormatterUtil;

public class UpdateStockViewHolder extends BaseViewHolder<Stock> {

    @BindView(R.id.stock_item_name)
    TextView productName;

    @BindView(R.id.stock_item_quantity)
    TextView quantity;

    @BindView(R.id.stock_item_purchase_price)
    TextView purchasePrice;

    @BindView(R.id.stock_item_sale_price)
    TextView salePrice;

    private Stock stock;

    public UpdateStockViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(Stock stock) {
        this.stock = stock;

        productName.setText(stock.getProductDescription().getName());
        quantity.setText(stock.getQuantity().toString());
        purchasePrice.setText(FormatterUtil.mtFormat(stock.getPurchasePrice()));
        salePrice.setText(FormatterUtil.mtFormat(stock.getSalePrice()));
    }

    @Override
    public void setItemClickListner(ClickListner listner) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
