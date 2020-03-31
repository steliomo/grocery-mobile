package mz.co.commandline.grocery.report.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.report.adapter.SaleReportAdapter;
import mz.co.commandline.grocery.report.delegate.ReportDelegate;
import mz.co.commandline.grocery.util.FormatterUtil;

public class SaleReportFragment extends BaseFragment {

    @BindView(R.id.fragment_sale_report_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_sale_report_total_profit)
    TextView totalProfit;

    @BindView(R.id.fragment_sale_report_total_sale)
    TextView totalSale;

    @BindView(R.id.fragment_sale_report_textView)
    TextView reportTitle;

    private ReportDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_sale_report;
    }

    @Override
    public void onCreateView() {
        delegate = (ReportDelegate) getActivity();

        reportTitle.setText(delegate.getReportTitle());

        SaleReportAdapter adapter = new SaleReportAdapter(getActivity(), delegate.getSales());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        totalProfit.setText(FormatterUtil.mtFormat(delegate.getTotalProfit()));
        totalSale.setText(FormatterUtil.mtFormat(delegate.getTotalSale()));
    }
}
