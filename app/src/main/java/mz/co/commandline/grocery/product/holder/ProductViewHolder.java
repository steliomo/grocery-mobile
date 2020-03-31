package mz.co.commandline.grocery.product.holder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.holder.BaseViewHolder;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.product.dto.ProductDTO;

public class ProductViewHolder extends BaseViewHolder<ProductDTO> {

    @BindView(R.id.product_name)
    TextView productName;

    private ClickListner listner;

    private ProductDTO productDTO;

    public ProductViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(ProductDTO productDTO) {
        this.productDTO = productDTO;
        productName.setText(productDTO.getName());
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClickListner(productDTO);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
