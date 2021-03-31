package mz.co.commandline.grocery.sale.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.holder.BaseViewHolder;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.sale.dto.SaleItemDTO;
import mz.co.commandline.grocery.util.FormatterUtil;

public class SaleItemViewHolder extends BaseViewHolder<SaleItemDTO> {

    @BindView(R.id.sale_item_name)
    TextView itemName;

    @BindView(R.id.sale_item_quantity)
    TextView itemQuantity;

    @BindView(R.id.sale_item_value)
    TextView itemValue;

    @BindView(R.id.sale_item_discount)
    TextView itemDiscount;

    @BindView(R.id.sale_item_icon)
    ImageView saleItemIcon;

    private ClickListner listner;

    private SaleItemDTO saleItem;

    public SaleItemViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(SaleItemDTO saleItem) {
        this.saleItem = saleItem;

        itemName.setText(saleItem.getSaleableItemDTO().getName());
        itemQuantity.setText(saleItem.getQuantity().toString());
        itemValue.setText(FormatterUtil.mtFormat(saleItem.getTotal()));
        itemDiscount.setText(FormatterUtil.mtFormat(saleItem.getDiscount()));

        if (ItemType.SERVICE.equals(saleItem.getSaleableItemDTO().getSalableItemType())) {
            saleItemIcon.setImageResource(R.mipmap.ic_services);
        }
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
