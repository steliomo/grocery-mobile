package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.report.ReportType;
import mz.co.commandline.grocery.report.delegate.ReportDelegate;
import mz.co.commandline.grocery.report.fragment.PeriodSelectionFragment;
import mz.co.commandline.grocery.report.fragment.ReportMenuFragment;
import mz.co.commandline.grocery.report.fragment.SaleReportFragment;
import mz.co.commandline.grocery.report.fragment.StockReportFragment;
import mz.co.commandline.grocery.sale.dto.SalesDTO;
import mz.co.commandline.grocery.sale.service.SaleService;
import mz.co.commandline.grocery.stock.dto.StockDTO;
import mz.co.commandline.grocery.stock.service.StockService;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.DateUtil;
import mz.co.commandline.grocery.util.FormatterUtil;
import mz.co.commandline.grocery.util.FragmentUtil;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertType;

public class ReportActivity extends BaseAuthActivity implements View.OnClickListener, ReportDelegate {

    @Inject
    SaleService saleService;

    @Inject
    StockService stockService;

    @Inject
    UserService userService;

    private FragmentManager fragmentManager;

    private AlertDialogManager alertDialogManager;

    private ProgressDialog progressBar;

    private SalesDTO sales;

    private List<StockDTO> stocks;

    private Toolbar toolbar;

    private ReportType reportType;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_report);

        toolbar = getToolbar();
        toolbar.setTitle(R.string.reports);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        fragmentManager = getSupportFragmentManager();

        alertDialogManager = new AlertDialogManager(this);

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        FragmentUtil.displayFragment(fragmentManager, R.id.report_activity_framelayout, new ReportMenuFragment(), Boolean.FALSE);
    }

    @Override
    public void onClick(View view) {
        FragmentUtil.popBackStack(fragmentManager, this);
    }

    @Override
    public SalesDTO getSales() {
        return sales;
    }

    @Override
    public List<StockDTO> getStocks() {
        return stocks;
    }

    @Override
    public void displaySalesReport() {
        reportType = ReportType.SALES_REPORT;
        FragmentUtil.displayFragment(fragmentManager, R.id.report_activity_framelayout, new PeriodSelectionFragment(), Boolean.TRUE);
    }

    @Override
    public void displayProductOnStock() {
        progressBar.show();

        stockService.findAllStocksByGrocery(userService.getGroceryDTO(), new ResponseListner<List<StockDTO>>() {
            @Override
            public void success(List<StockDTO> response) {
                progressBar.dismiss();

                if (response.isEmpty()) {
                    alertDialogManager.dialog(AlertType.ERROR, "Estabelecimento sem stocks registados!", null);
                    return;
                }

                stocks = response;
                FragmentUtil.displayFragment(fragmentManager, R.id.report_activity_framelayout, new StockReportFragment(), Boolean.TRUE);
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
    public void displayRecommendedProductsToAcquire() {
        progressBar.show();

        stockService.findLowStocksByGroceryAndSalePeriod(userService.getGroceryDTO(), DateUtil.getFirstDateOfTheYear(), DateUtil.getLastDateOfTheYear(), new ResponseListner<List<StockDTO>>() {
            @Override
            public void success(List<StockDTO> response) {
                progressBar.dismiss();

                if (response.isEmpty()) {
                    alertDialogManager.dialog(AlertType.ERROR, "Estabelecimento sem stocks baixos!", null);
                    return;
                }

                stocks = response;
                FragmentUtil.displayFragment(fragmentManager, R.id.report_activity_framelayout, new StockReportFragment(), Boolean.TRUE);
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
    public void displayReport(String startDate, String endDate) {
        switch (reportType) {

            case SALES_REPORT:
                salesReport(startDate, endDate);
                break;
        }
    }

    private void salesReport(String startDate, String endDate) {
        progressBar.show();

        saleService.findSalesPerPeriod(userService.getGroceryDTO().getUuid(), startDate, endDate, new ResponseListner<SalesDTO>() {
            @Override
            public void success(SalesDTO response) {
                progressBar.dismiss();

                if (response.getSalesReport().isEmpty()) {
                    alertDialogManager.dialog(AlertType.ERROR, "Nenhuma venada foi realizada no período especificado!", null);
                    return;
                }

                sales = response;
                FragmentUtil.displayFragment(fragmentManager, R.id.report_activity_framelayout, new SaleReportFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                alertDialogManager.dialog(AlertType.ERROR, "Ocorreu um erro ao processar os dados. Por favor tente novamente", null);
                Log.e("REPORT_SALES_PER_PERIOD", message);
            }
        });
    }

    @Override
    public void onItemClick(StockDTO stockDTO) {

        final AlertDialog dialog = new AlertDialog.Builder(this).setView(R.layout.stock_details)
                .setCancelable(Boolean.FALSE).create();

        dialog.show();

        TextView productName = dialog.findViewById(R.id.stock_details_product_name);
        TextView purchasePrice = dialog.findViewById(R.id.stock_details_purchase_price);
        TextView salePrice = dialog.findViewById(R.id.stock_details_sale_price);
        TextView minimumStock = dialog.findViewById(R.id.stock_details_minimum_stock);
        TextView quantity = dialog.findViewById(R.id.stock_details_quantity);

        productName.setText(stockDTO.getProductDescriptionDTO().getName());
        purchasePrice.setText(FormatterUtil.mtFormat(new BigDecimal(stockDTO.getPurchasePrice())));
        salePrice.setText(FormatterUtil.mtFormat(new BigDecimal(stockDTO.getSalePrice())));
        minimumStock.setText(stockDTO.getMinimumStock());
        quantity.setText(stockDTO.getQuantity());

        Button okBtn = dialog.findViewById(R.id.stock_details_ok_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
