package mz.co.commandline.grocery.product.holder;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.holder.BaseViewHolder;
import mz.co.commandline.grocery.product.model.Product;

public class ProductViewHolder extends BaseViewHolder<Product> {

    @BindView(R.id.product_name)
    TextView productName;

    private ClickListner listner;

    private Product product;

    public ProductViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(Product product) {
        this.product = product;
        productName.setText(product.getName());
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClickListner(product);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
