package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.co.commandline.grocery.Listner.ResponseListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.report.delegate.ReportDelegate;
import mz.co.commandline.grocery.report.fragment.ReportMenuFragment;
import mz.co.commandline.grocery.report.fragment.SaleReportFragment;
import mz.co.commandline.grocery.report.fragment.StockReportFragment;
import mz.co.commandline.grocery.sale.model.SaleReport;
import mz.co.commandline.grocery.sale.service.SaleService;
import mz.co.commandline.grocery.stock.model.Stock;
import mz.co.commandline.grocery.stock.service.StockService;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertType;

public class ReportActivity extends BaseAuthActivity implements View.OnClickListener, ReportDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    SaleService saleService;

    @Inject
    StockService stockService;

    private FragmentManager fragmentManager;

    private AlertDialogManager alertDialogManager;

    private ProgressDialog progressBar;

    private List<SaleReport> sales;

    private List<Stock> stocks;

    private BigDecimal totalProfit;

    private BigDecimal totalSale;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_report);

        toolbar.setTitle(R.string.reports);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        alertDialogManager = new AlertDialogManager(this);

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        displayFragment(new ReportMenuFragment(), Boolean.FALSE);
    }

    private void displayFragment(Fragment fragment, boolean onStack) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.report_activity_framelayout, fragment);

        if (onStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void onClick(View view) {

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return;
        }

        finish();
    }

    @Override
    public void displayLast7DaysReport() {
        progressBar.show();

        saleService.findLast7DaysSales(new ResponseListner<List<SaleReport>>() {
            @Override
            public void success(List<SaleReport> response) {
                progressBar.dismiss();

                if (response.isEmpty()) {
                    alertDialogManager.dialog(AlertType.ERROR, "Nenhuma venda foi realizada nos ulitmos 7 dias!", null);
                    return;
                }

                sales = response;
                displayFragment(new SaleReportFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                alertDialogManager.dialog(AlertType.ERROR, "Ocorreu um erro ao processar os dados. Por favor tente novamente", null);
                Log.e("REPORT_SALES", message);
            }
        });

    }

    @Override
    public void displayProductStocks() {
        progressBar.show();

        stockService.findAllStocks(new ResponseListner<List<Stock>>() {
            @Override
            public void success(List<Stock> response) {
                progressBar.dismiss();

                if (response.isEmpty()) {
                    alertDialogManager.dialog(AlertType.ERROR, "Estabelecimento sem stocks registados!", null);
                    return;
                }

                stocks = response;
                displayFragment(new StockReportFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                alertDialogManager.dialog(AlertType.ERROR, "Ocorreu um erro ao processar os dados. Por favor tente novamente", null);
                Log.e("REPORT_STOCKS", message);
            }
        });
    }

    @Override
    public List<SaleReport> getSales() {
        calculateProfitAndSaleTotal();
        return sales;
    }

    private void calculateProfitAndSaleTotal() {
        totalProfit = BigDecimal.ZERO;
        totalSale = BigDecimal.ZERO;

        for (SaleReport sale : sales) {
            totalProfit = totalProfit.add(sale.getProfit());
            totalSale = totalSale.add(sale.getSale());
        }
    }

    @Override
    public List<Stock> getStocks() {
        return stocks;
    }

    @Override
    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    @Override
    public BigDecimal getTotalSale() {
        return totalSale;
    }
}
