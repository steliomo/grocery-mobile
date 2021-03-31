package mz.co.commandline.grocery.report.fragment;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.report.delegate.ReportDelegate;
import mz.co.commandline.grocery.user.dto.UserRole;


public class ReportMenuFragment extends BaseFragment {

    @BindView(R.id.fragment_report_menu_most_wanted_low_stocks_btn)
    Button lowStockBtn;

    @BindView(R.id.fragment_report_menu_products_on_stock_btn)
    Button stockBtn;


    private ReportDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_report_menu;
    }

    @Override
    public void onCreateView() {
        delegate = (ReportDelegate) getActivity();

        if (hasRole(UserRole.OPERATOR)) {
            lowStockBtn.setVisibility(View.INVISIBLE);
            stockBtn.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.fragment_report_menu_sales_btn)
    public void onClickSalesBtn() {
        delegate.displaySalesReport();
    }

    @OnClick(R.id.fragment_report_menu_products_on_stock_btn)
    public void onClickProductsOnStockBtn() {
        delegate.displayProductOnStock();
    }

    @OnClick(R.id.fragment_report_menu_most_wanted_low_stocks_btn)
    public void onClickMostWantedLowStockBtn() {
        delegate.displayRecommendedProductsToAcquire();
    }

    @Override
    public String getTitle() {
        return getString(R.string.reports);
    }
}
