package mz.co.commandline.grocery.report.fragment;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.report.delegate.ReportDelegate;
import mz.co.commandline.grocery.user.model.UserRole;


public class ReportMenuFragment extends BaseFragment {

    @BindView(R.id.fragment_report_menu_stocks_btn)
    Button stockBtn;


    private ReportDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_report_menu;
    }

    @Override
    public void onCreateView() {
        delegate = (ReportDelegate) getActivity();

        if (delegate.hasRole(UserRole.OPERATOR)) {
            stockBtn.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.fragment_report_menu_last_7days_btn)
    public void onClickLast7DaysBtn() {
        delegate.displayLast7DaysReport();
    }

    @OnClick(R.id.fragment_report_menu_stocks_btn)
    public void onClickStocksBtn() {
        delegate.displayProductStocks();
    }

    @OnClick(R.id.fragment_report_menu_per_period_btn)
    public void onClickPerPeriodBtn() {
        delegate.displayPeriodSelectionFragment();
    }
}
