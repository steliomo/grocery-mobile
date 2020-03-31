package mz.co.commandline.grocery.stock.holder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.holder.BaseViewHolder;
import mz.co.commandline.grocery.stock.dto.StockDTO;

public class StockViewHolder extends BaseViewHolder<StockDTO> {

    @BindView(R.id.product_name)
    TextView productName;

    private ClickListner listner;

    private StockDTO stockDTO;

    public StockViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(StockDTO stockDTO) {
        this.stockDTO = stockDTO;
        productName.setText(stockDTO.getProductDescriptionDTO().getName());
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClickListner(stockDTO);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
