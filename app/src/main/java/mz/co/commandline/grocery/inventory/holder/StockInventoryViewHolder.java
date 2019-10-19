package mz.co.commandline.grocery.inventory.holder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.holder.BaseViewHolder;
import mz.co.commandline.grocery.inventory.dto.StockInventoryDTO;
import mz.co.commandline.grocery.listner.ClickListner;

public class StockInventoryViewHolder extends BaseViewHolder<StockInventoryDTO> {

    @BindView(R.id.inventory_item_name)
    TextView itemName;

    @BindView(R.id.inventory_item_quantity)
    TextView quantity;

    @BindView(R.id.inventory_item_difference)
    TextView difference;


    public StockInventoryViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(StockInventoryDTO stockInventory) {
        itemName.setText(stockInventory.getStockDTO().getProductDescriptionDTO().getName());
        quantity.setText(stockInventory.getFisicalInventory());
        difference.setText(stockInventory.getDiffence());
    }

    @Override
    public void setItemClickListner(ClickListner listner) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
