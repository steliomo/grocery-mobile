package mz.co.commandline.grocery.saleable.fragment;

import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.saleable.delegate.SaleableItemActionDelegate;
import mz.co.commandline.grocery.saleable.dto.ActionType;


public class ProductsAndStocksFragment extends BaseFragment {

    private SaleableItemActionDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_products_and_stocks;
    }

    @Override
    public void onCreateView() {
        delegate = (SaleableItemActionDelegate) getActivity();
    }

    @OnClick(R.id.fragment_products_and_stocks_add_new_products_btn)
    public void onClickAddNewProductBtn() {
        delegate.selectedAction(ActionType.ADD);
        delegate.prepareSaleable();
    }

    @OnClick(R.id.fragment_products_and_stocks_update_stocks_btn)
    public void onClickUpdateStocskAndPricesBtn() {
        delegate.selectedAction(ActionType.UPDATE);
        delegate.prepareSaleable();
    }

    @Override
    public String getTitle() {
        return getString(R.string.products_and_services);
    }
}
