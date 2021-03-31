package mz.co.commandline.grocery.report.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.report.adapter.StockReportAdapter;
import mz.co.commandline.grocery.report.delegate.ReportDelegate;
import mz.co.commandline.grocery.saleable.dto.StockDTO;

public class StockReportFragment extends BaseFragment implements ClickListner<StockDTO>, SearchView.OnQueryTextListener {

    @BindView(R.id.fragment_stock_report_recycleView)
    RecyclerView recyclerView;

    private ReportDelegate delegate;

    private StockReportAdapter adapter;


    @Override
    public int getResourceId() {
        return R.layout.fragment_stock_report;
    }

    @Override
    public void onCreateView() {

        Toolbar toolBar = getToolBar();
        toolBar.inflateMenu(R.menu.search_menu);

        MenuItem menuItem = toolBar.getMenu().findItem(R.id.search_menu_action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(this);

        delegate = (ReportDelegate) getActivity();
        adapter = new StockReportAdapter(getActivity(), delegate.getStocks());
        adapter.setItemClickListner(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public String getTitle() {
        return getString(R.string.products_and_services);
    }

    @Override
    public void onClickListner(StockDTO stockDTO) {
        delegate.onItemClick(stockDTO);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        adapter.getFilter().filter(query);
        return false;
    }
}
