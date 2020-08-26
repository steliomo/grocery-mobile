package mz.co.commandline.grocery.stock.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.product.delegate.ProductDelegate;
import mz.co.commandline.grocery.stock.adapter.UpdateStockAdapter;
import mz.co.commandline.grocery.stock.delegate.ProductsAndStocksDelegate;
import mz.co.commandline.grocery.stock.delegate.UpdateStockDelegate;


public class UpdateStockFragment extends BaseFragment {

    @BindView(R.id.fragment_update_stock_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_update_stock_update_btn)
    Button button;

    private UpdateStockDelegate delegate;

    private ProductDelegate productDelegate;

    private ProductsAndStocksDelegate productsAndStocksDelegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_update_stock;
    }

    @Override
    public void onCreateView() {
        delegate = (UpdateStockDelegate) getActivity();
        productDelegate = (ProductDelegate) getActivity();
        productsAndStocksDelegate = (ProductsAndStocksDelegate) getActivity();

        getToolBar().setTitle(productsAndStocksDelegate.getTiTle());
        button.setText(productsAndStocksDelegate.getActionName());

        UpdateStockAdapter adapter = new UpdateStockAdapter(getActivity(), delegate.updatedStocksAndPrices());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fragment_update_stock_select_item_btn)
    public void onClickSelectProductBtn() {
        productDelegate.selectProduct();
    }

    @OnClick(R.id.fragment_update_stock_update_btn)
    public void onClickAdd() {
        delegate.updateStocksAndPrices();
    }
}
