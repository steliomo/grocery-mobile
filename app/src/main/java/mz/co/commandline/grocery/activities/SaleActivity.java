package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.co.commandline.grocery.Listner.ResponseListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.product.fragment.ProductFragment;
import mz.co.commandline.grocery.product.model.Product;
import mz.co.commandline.grocery.product.service.ProductService;
import mz.co.commandline.grocery.sale.delegate.SaleDelegate;
import mz.co.commandline.grocery.sale.fragment.AddSaleItemFragment;
import mz.co.commandline.grocery.sale.fragment.SaleRegistFragment;
import mz.co.commandline.grocery.sale.model.Sale;
import mz.co.commandline.grocery.sale.model.SaleItem;
import mz.co.commandline.grocery.sale.service.SaleService;
import mz.co.commandline.grocery.stock.fragment.StockFragment;
import mz.co.commandline.grocery.stock.model.Stock;
import mz.co.commandline.grocery.stock.service.StockService;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

public class SaleActivity extends BaseAuthActivity implements SaleDelegate, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ProductService productService;

    @Inject
    StockService stockService;

    @Inject
    SaleService saleService;

    private FragmentManager fragmentManager;

    private ProgressDialog progressBar;

    private List<Product> products;

    private List<Stock> stocks;

    private Stock stock;

    private Sale sale;

    private AlertDialogManager dialogManager;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_sale);

        toolbar.setTitle(R.string.sale_regist);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        sale = new Sale();

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        dialogManager = new AlertDialogManager(this);

        showFragment(new SaleRegistFragment(), Boolean.FALSE);
    }

    private void showFragment(Fragment fragment, boolean onStack) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.sale_activity_frame_layout, fragment);

        if (onStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void addItem() {
        progressBar.show();

        productService.findAllProducts(new ResponseListner<List<Product>>() {
            @Override
            public void success(List<Product> response) {
                progressBar.dismiss();
                products = response;
                showFragment(new ProductFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_error_on_add_item), null);
                progressBar.dismiss();
                Log.e("PRODUCTS", message);
            }
        });

    }

    @Override
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public void selectedProduct(Product product) {

        progressBar.show();
        stockService.findProductStocksByProduct(product, new ResponseListner<List<Stock>>() {
            @Override
            public void success(List<Stock> response) {
                progressBar.dismiss();

                if (response.isEmpty()) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_no_stock_for_the_product_selected), null);
                    return;
                }

                stocks = response;
                showFragment(new StockFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_error_on_selecting_product), null);
                Log.e("STOCKS", message);
            }
        });


    }

    @Override
    public List<Stock> getStocks() {
        return Collections.unmodifiableList(stocks);
    }

    @Override
    public void selectedStock(Stock stock) {
        this.stock = stock;
        showFragment(new AddSaleItemFragment(), Boolean.TRUE);
    }

    @Override
    public Stock getStock() {
        return stock;
    }

    @Override
    public void addSaleItem(SaleItem saleItem) {
        sale.addSaleItem(saleItem);
        resetFragment();
    }

    private void resetFragment() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        showFragment(new SaleRegistFragment(), Boolean.FALSE);
    }

    @Override
    public Sale getSale() {
        return sale;
    }

    @Override
    public void cancel() {
        resetFragment();
    }

    @Override
    public void registSale() {

        if (sale.getItems().isEmpty()) {
            dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_without_item_not_allowed), null);
            return;
        }

        progressBar.show();

        saleService.registSale(sale, new ResponseListner<Sale>() {
            @Override
            public void success(Sale response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.sale_registed_with_success), new AlertListner() {
                    @Override
                    public void perform() {
                        finish();
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_error_on_regist), null);
                Log.e("SALE", message);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return;
        }

        finish();
    }
}
