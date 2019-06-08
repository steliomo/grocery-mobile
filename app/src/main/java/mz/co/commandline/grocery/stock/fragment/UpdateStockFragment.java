package mz.co.commandline.grocery.stock.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.stock.adapter.UpdateStockAdapter;
import mz.co.commandline.grocery.stock.delegate.StockDelegate;


public class UpdateStockFragment extends BaseFragment {

    @BindView(R.id.fragment_update_stock_recyclerview)
    RecyclerView recyclerView;

    private StockDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_update_stock;
    }

    @Override
    public void onCreateView() {
        delegate = (StockDelegate) getActivity();
        UpdateStockAdapter adapter = new UpdateStockAdapter(getActivity(), delegate.updatedStocksAndPrices());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fragment_update_stock_select_item_btn)
    public void onClickSelectProductBtn() {
        delegate.addItem();
    }

    @OnClick(R.id.fragment_update_stock_update_btn)
    public void onClickAdd() {
        delegate.updateStocksAndPrices();
    }
}
