package mz.co.commandline.grocery.report.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.report.adapter.StockReportAdapter;
import mz.co.commandline.grocery.report.delegate.ReportDelegate;

public class StockReportFragment extends BaseFragment {

    @BindView(R.id.fragment_stock_report_recycleView)
    RecyclerView recyclerView;


    @Override
    public int getResourceId() {
        return R.layout.fragment_stock_report;
    }

    @Override
    public void onCreateView() {
        ReportDelegate delegate = (ReportDelegate) getActivity();
        StockReportAdapter adapter = new StockReportAdapter(getActivity(), delegate.getStocks());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}
