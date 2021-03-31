package mz.co.commandline.grocery.saleable.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.holder.BaseViewHolder;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.item.dto.ItemType;

public class StockViewHolder extends BaseViewHolder<SaleableItemDTO> {

    @BindView(R.id.product_name)
    TextView productName;

    @BindView(R.id.product_icon)
    ImageView productIcon;

    private ClickListner listner;

    private SaleableItemDTO saleableItem;

    public StockViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(SaleableItemDTO saleableItem) {
        this.saleableItem = saleableItem;
        productName.setText(saleableItem.getName());

        if (ItemType.SERVICE.equals(saleableItem.getSalableItemType())) {
            productIcon.setImageResource(R.mipmap.ic_services);
        }
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClickListner(saleableItem);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
