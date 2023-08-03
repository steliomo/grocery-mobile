package mz.co.commandline.grocery.sale.fragment;

import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.item.delegate.ItemDelegate;
import mz.co.commandline.grocery.item.dto.ItemType;

public class ItemTypeFragment extends BaseFragment {

    private ItemDelegate itemDelegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_item_type;
    }

    @Override
    public void onCreateView() {
        itemDelegate = (ItemDelegate) getActivity();
    }

    @OnClick(R.id.fragment_item_type_products_view)
    public void selectProduct() {
        itemDelegate.selectItemType(ItemType.PRODUCT);
    }

    @OnClick(R.id.fragment_item_type_services_view)
    public void selectService() {
        itemDelegate.selectItemType(ItemType.SERVICE);
    }

    @Override
    public String getTitle() {
        return getString(R.string.products_and_services);
    }
}