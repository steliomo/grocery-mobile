package mz.co.commandline.grocery.report.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.holder.BaseViewHolder;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.stock.dto.StockDTO;

public class StockReportViewHolder extends BaseViewHolder<StockDTO> {

    @BindView(R.id.stock_report_ord)
    TextView ord;

    @BindView(R.id.stock_report_product)
    TextView product;

    @BindView(R.id.stock_report_quantity)
    TextView quantity;

    private ClickListner listner;

    private StockDTO stockDTO;

    public StockReportViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(StockDTO stockDTO) {
        this.stockDTO = stockDTO;
        ord.setText(stockDTO.getPosition());
        product.setText(stockDTO.getProductDescriptionDTO().getName());
        quantity.setText(stockDTO.getQuantity().toString());

        if (stockDTO.isLow()) {
            quantity.setTextColor(Color.parseColor("#D32F2F"));
            return;
        }

        quantity.setTextColor(product.getCurrentTextColor());
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClickListner(stockDTO);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
