package mz.co.commandline.grocery.stock.holder;

import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;

import butterknife.BindView;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.holder.BaseViewHolder;
import mz.co.commandline.grocery.stock.dto.StockDTO;
import mz.co.commandline.grocery.util.FormatterUtil;

public class UpdateStockViewHolder extends BaseViewHolder<StockDTO> {

    @BindView(R.id.stock_item_name)
    TextView productName;

    @BindView(R.id.stock_item_quantity)
    TextView quantity;

    @BindView(R.id.stock_item_purchase_price)
    TextView purchasePrice;

    @BindView(R.id.stock_item_sale_price)
    TextView salePrice;

    private StockDTO stockDTO;

    public UpdateStockViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(StockDTO stockDTO) {
        this.stockDTO = stockDTO;

        productName.setText(stockDTO.getProductDescriptionDTO().getName());
        quantity.setText(stockDTO.getQuantity().toString());
        purchasePrice.setText(FormatterUtil.mtFormat(new BigDecimal(stockDTO.getPurchasePrice())));
        salePrice.setText(FormatterUtil.mtFormat(new BigDecimal(stockDTO.getSalePrice())));
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
