package mz.co.commandline.grocery.product.fragment;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import mz.co.commandline.grocery.Listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.product.adapter.ProductAdapter;
import mz.co.commandline.grocery.product.model.Product;
import mz.co.commandline.grocery.sale.delegate.SaleDelegate;


public class ProductFragment extends BaseFragment implements ClickListner<Product> {

    @BindView(R.id.fragment_product_recycleview)
    RecyclerView recyclerView;

    private SaleDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_product;
    }

    @Override
    public void onCreateView() {
        delegate = (SaleDelegate) getActivity();
        ProductAdapter adapter = new ProductAdapter(getActivity(), delegate.getProducts());
        adapter.setItemClickListner(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListner(Product product) {
        delegate.selectedProduct(product);
    }
}
