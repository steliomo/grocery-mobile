package mz.co.commandline.grocery.sale.holder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.holder.BaseViewHolder;
import mz.co.commandline.grocery.sale.model.SaleItem;
import mz.co.commandline.grocery.util.FormatterUtil;

public class SaleItemViewHolder extends BaseViewHolder<SaleItem> {

    @BindView(R.id.sale_item_name)
    TextView itemName;

    @BindView(R.id.sale_item_quantity)
    TextView itemQuantity;

    @BindView(R.id.sale_item_value)
    TextView itemValue;

    @BindView(R.id.sale_item_discount)
    TextView itemDiscount;

    private ClickListner listner;

    private SaleItem saleItem;

    public SaleItemViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(SaleItem saleItem) {
        this.saleItem = saleItem;

        itemName.setText(saleItem.getStock().getProductDescription().getName());
        itemQuantity.setText(saleItem.getQuantity().toString());
        itemValue.setText(FormatterUtil.mtFormat(saleItem.getTotal()));
        itemDiscount.setText(FormatterUtil.mtFormat(saleItem.getDiscount()));
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
