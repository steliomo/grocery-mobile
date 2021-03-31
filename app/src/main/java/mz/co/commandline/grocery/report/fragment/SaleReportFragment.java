package mz.co.commandline.grocery.report.fragment;

import android.graphics.Color;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.report.adapter.SaleReportAdapter;
import mz.co.commandline.grocery.report.delegate.ReportDelegate;
import mz.co.commandline.grocery.sale.dto.SalesDTO;
import mz.co.commandline.grocery.util.FormatterUtil;

public class SaleReportFragment extends BaseFragment {

    @BindView(R.id.fragment_sale_report_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_sale_report_total_billing)
    TextView totalBilling;

    @BindView(R.id.fragment_sale_report_total_sale)
    TextView totalSale;

    @BindView(R.id.fragment_sale_report_expenses)
    TextView expenses;

    @BindView(R.id.fragment_sale_report_profit)
    TextView profit;

    private ReportDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_sale_report;
    }

    @Override
    public void onCreateView() {
        delegate = (ReportDelegate) getActivity();
        SalesDTO sales = delegate.getSales();
        SaleReportAdapter adapter = new SaleReportAdapter(getActivity(), sales.getSalesReport());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        totalBilling.setText(FormatterUtil.mtFormat(sales.getBilling()));
        totalSale.setText(FormatterUtil.mtFormat(sales.getSales()));
        expenses.setText(FormatterUtil.mtFormat(sales.getExpense()));
        profit.setText(FormatterUtil.mtFormat(sales.getProfit()));

        if (sales.getProfit().doubleValue() == 0) {
            profit.setTextColor(Color.parseColor("#D32F2F"));
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.sales_report);
    }
}
