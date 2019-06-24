package mz.co.commandline.grocery.stock.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.delegate.SaleAndStockDelegate;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.stock.adapter.StockAdapter;
import mz.co.commandline.grocery.stock.model.Stock;


public class StockFragment extends BaseFragment implements ClickListner<Stock> {

    @BindView(R.id.fragment_stock_recycleview)
    RecyclerView recyclerView;

    private SaleAndStockDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_stock;
    }

    @Override
    public void onCreateView() {
        delegate = (SaleAndStockDelegate) getActivity();
        StockAdapter adapter = new StockAdapter(getActivity(), delegate.getStocks());
        adapter.setItemClickListner(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListner(Stock stock) {
        delegate.selectedStock(stock);
    }
}
