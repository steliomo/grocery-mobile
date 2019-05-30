package mz.co.commandline.grocery.report.fragment;

import android.support.v4.app.FragmentActivity;

import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.report.delegate.ReportDelegate;


public class ReportMenuFragment extends BaseFragment {


    private ReportDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_report_menu;
    }

    @Override
    public void onCreateView() {
         delegate = (ReportDelegate) getActivity();
    }

    @OnClick(R.id.fragment_report_menu_last_7days_btn)
    public void onClickLast7DaysBtn() {
        delegate.displayLast7DaysReport();
    }

    @OnClick(R.id.fragment_report_menu_stocks_btn)
    public void onClickStocksBtn() {
        delegate.displayProductStocks();
    }
}
