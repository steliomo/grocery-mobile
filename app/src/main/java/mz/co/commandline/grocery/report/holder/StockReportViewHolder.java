package mz.co.commandline.grocery.report.holder;

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

    public StockReportViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(StockDTO stockDTO) {
        ord.setText(stockDTO.getPosition());
        product.setText(stockDTO.getProductDescriptionDTO().getName());
        quantity.setText(stockDTO.getQuantity().toString());
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
