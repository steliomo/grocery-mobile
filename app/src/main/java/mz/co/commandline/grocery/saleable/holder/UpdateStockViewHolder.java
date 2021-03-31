package mz.co.commandline.grocery.saleable.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.holder.BaseViewHolder;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.saleable.dto.StockDTO;
import mz.co.commandline.grocery.util.FormatterUtil;

public class UpdateStockViewHolder extends BaseViewHolder<SaleableItemDTO> {

    @BindView(R.id.stock_item_name)
    TextView productName;

    @BindView(R.id.stock_item_quantity)
    TextView quantity;

    @BindView(R.id.stock_item_purchase_price)
    TextView purchasePrice;

    @BindView(R.id.stock_item_sale_price)
    TextView salePrice;

    @BindView(R.id.stock_item_icon)
    ImageView saleableItemIcon;

    private SaleableItemDTO saleableItem;

    public UpdateStockViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(SaleableItemDTO saleableItem) {
        this.saleableItem = saleableItem;

        productName.setText(saleableItem.getName());
        salePrice.setText(FormatterUtil.mtFormat(new BigDecimal(saleableItem.getSalePrice())));

        if (ItemType.PRODUCT.equals(saleableItem.getSalableItemType())) {
            StockDTO stock = (StockDTO) saleableItem;

            quantity.setText(stock.getQuantity().toString());
            purchasePrice.setText(FormatterUtil.mtFormat(new BigDecimal(stock.getPurchasePrice())));

            return;
        }

        saleableItemIcon.setImageResource(R.mipmap.ic_services);
        quantity.setText("N/A");
        purchasePrice.setText("N/A");
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
