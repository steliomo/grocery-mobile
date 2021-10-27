package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.expense.dto.ExpenseDTO;
import mz.co.commandline.grocery.expense.dto.ExpenseReport;
import mz.co.commandline.grocery.expense.dto.ExpensesDTO;
import mz.co.commandline.grocery.expense.service.ExpenseService;
import mz.co.commandline.grocery.generics.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.main.delegate.MenuDelegate;
import mz.co.commandline.grocery.main.fragment.MenuFragment;
import mz.co.commandline.grocery.menu.Menu;
import mz.co.commandline.grocery.menu.MenuItem;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.report.ReportType;
import mz.co.commandline.grocery.report.delegate.ReportDelegate;
import mz.co.commandline.grocery.report.fragment.CostsFragment;
import mz.co.commandline.grocery.report.fragment.PeriodSelectionFragment;
import mz.co.commandline.grocery.report.fragment.SaleReportFragment;
import mz.co.commandline.grocery.report.fragment.StockReportFragment;
import mz.co.commandline.grocery.sale.dto.SalesDTO;
import mz.co.commandline.grocery.sale.service.SaleService;
import mz.co.commandline.grocery.saleable.dto.StockDTO;
import mz.co.commandline.grocery.saleable.service.StockService;
import mz.co.commandline.grocery.user.dto.UserRole;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.DateUtil;
import mz.co.commandline.grocery.util.FormatterUtil;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertType;

public class ReportActivity extends BaseAuthActivity implements View.OnClickListener, ReportDelegate, MenuDelegate {

    @Inject
    SaleService saleService;

    @Inject
    StockService stockService;

    @Inject
    UserService userService;

    @Inject
    ExpenseService expenseService;

    private AlertDialogManager alertDialogManager;

    private ProgressDialog progressBar;

    private SalesDTO sales;

    private List<StockDTO> stocks;

    private Toolbar toolbar;

    private ReportType reportType;

    private Menu menu;

    private String startDate;

    private String endDate;

    private List<ExpenseReport> expensesReport;

    private BigDecimal totalExpense;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_report);

        toolbar = getToolbar();
        toolbar.setTitle(R.string.reports);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        alertDialogManager = new AlertDialogManager(this);

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        menu = new Menu();
        menu.addMenuItem(new MenuItem(R.string.sales, R.mipmap.ic_sale));

        if (!UserRole.OPERATOR.equals(userService.getGroceryUser().getUserRole())) {
            menu.addMenuItem(new MenuItem(R.string.expenses, R.mipmap.ic_costs));
            menu.addMenuItem(new MenuItem(R.string.stocks, R.mipmap.ic_stock));
            menu.addMenuItem(new MenuItem(R.string.bestsellers, R.mipmap.ic_bestseller));
        }

        showFragment(new MenuFragment(), Boolean.FALSE);
    }

    @Override
    public void onClick(View view) {
        popBackStack();
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
                showFragment(new StockReportFragment(), Boolean.TRUE);
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
                showFragment(new StockReportFragment(), Boolean.TRUE);
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

            case COSTS_REPORT:
                this.startDate = startDate;
                this.endDate = endDate;

                progressBar.show();
                expenseService.findExpensesByUnitAndPeriod(userService.getGroceryDTO().getUuid(), startDate, endDate, new ResponseListner<ExpensesDTO>() {
                    @Override
                    public void success(ExpensesDTO response) {
                        progressBar.dismiss();
                        expensesReport = response.getExpensesReport();
                        totalExpense = response.getTotalValue();
                        showFragment(new CostsFragment(), Boolean.TRUE);
                    }

                    @Override
                    public void error(String message) {
                        progressBar.dismiss();
                        dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_loading_expenses), null);
                        Log.e("EXPENSES", message);
                    }
                });
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
                showFragment(new SaleReportFragment(), Boolean.TRUE);
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

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public List<ExpenseReport> expensesReport() {
        return expensesReport;
    }

    @Override
    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    @Override
    public int getActivityFrameLayoutId() {
        return R.id.report_activity_framelayout;
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return menu.getMenuItems();
    }

    @Override
    public void onClickMenuItem(MenuItem menuItem) {

        switch (menuItem.getIconId()) {
            case R.mipmap.ic_sale:
                reportType = ReportType.SALES_REPORT;
                showFragment(new PeriodSelectionFragment(), Boolean.TRUE);
                break;

            case R.mipmap.ic_stock:
                displayProductOnStock();
                break;

            case R.mipmap.ic_bestseller:
                displayRecommendedProductsToAcquire();
                break;

            case R.mipmap.ic_costs:
                reportType = ReportType.COSTS_REPORT;
                showFragment(new PeriodSelectionFragment(), Boolean.TRUE);
                break;
        }
    }

    @Override
    public String getFragmentTitle() {
        return getString(R.string.reports);
    }
}
