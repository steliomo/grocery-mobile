package mz.co.commandline.grocery.saleable.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.saleable.delegate.SaleableItemDelegate;
import mz.co.commandline.grocery.saleable.adapter.StockAdapter;


public class StockFragment extends BaseFragment implements ClickListner<SaleableItemDTO> {

    @BindView(R.id.fragment_stock_recycleview)
    RecyclerView recyclerView;

    private SaleableItemDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_stock;
    }

    @Override
    public void onCreateView() {
        delegate = (SaleableItemDelegate) getActivity();
        StockAdapter adapter = new StockAdapter(getActivity(), delegate.getSaleableItems());
        adapter.setItemClickListner(this);

        Toolbar toolBar = getToolBar();
        setTitle(toolBar);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void setTitle(Toolbar toolBar) {
        if (ItemType.PRODUCT.equals(delegate.getItemType())) {
            toolBar.setTitle(R.string.stock_details);
            return;
        }
        toolBar.setTitle(R.string.service_details);
    }

    @Override
    public void onClickListner(SaleableItemDTO saleableItemDTO) {
        delegate.selectedSaleableItem(saleableItemDTO);
    }

    @Override
    public String getTitle() {
        return getString(R.string.stock_details);
    }
}
