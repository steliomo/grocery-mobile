package mz.co.commandline.grocery.stock.fragment;

import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.stock.delegate.ProductsAndStocksDelegate;


public class ProductsAndStocksFragment extends BaseFragment {

    private ProductsAndStocksDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_products_and_stocks;
    }

    @Override
    public void onCreateView() {
        delegate = (ProductsAndStocksDelegate) getActivity();
    }

    @OnClick(R.id.fragment_products_and_stocks_add_new_products_btn)
    public void onClickAddNewProductBtn() {
        delegate.addNewProducts();
    }

    @OnClick(R.id.fragment_products_and_stocks_update_stocks_btn)
    public void onClickUpdateStocskAndPricesBtn() {
        delegate.updateStockAndPrices();
    }

    @Override
    public String getTitle() {
        return getString(R.string.products_and_stocks);
    }
}
