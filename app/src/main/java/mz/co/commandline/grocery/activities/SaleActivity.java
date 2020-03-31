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
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.product.delegate.ProductDelegate;
import mz.co.commandline.grocery.product.dto.ProductDTO;
import mz.co.commandline.grocery.product.fragment.ProductFragment;
import mz.co.commandline.grocery.product.service.ProductService;
import mz.co.commandline.grocery.sale.delegate.SaleDelegate;
import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SaleItemDTO;
import mz.co.commandline.grocery.sale.fragment.AddSaleItemFragment;
import mz.co.commandline.grocery.sale.fragment.SaleRegistFragment;
import mz.co.commandline.grocery.sale.service.SaleService;
import mz.co.commandline.grocery.stock.delegate.StockDelegate;
import mz.co.commandline.grocery.stock.dto.StockDTO;
import mz.co.commandline.grocery.stock.fragment.StockFragment;
import mz.co.commandline.grocery.stock.service.StockService;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

public class SaleActivity extends BaseAuthActivity implements SaleDelegate, ProductDelegate, StockDelegate, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ProductService productService;

    @Inject
    StockService stockService;

    @Inject
    SaleService saleService;

    @Inject
    UserService userService;

    private FragmentManager fragmentManager;

    private ProgressDialog progressBar;

    private List<ProductDTO> productsDTO;

    private List<StockDTO> stocksDTO;

    private StockDTO stockDTO;

    private SaleDTO sale;

    private AlertDialogManager dialogManager;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_sale);

        toolbar.setTitle(R.string.sale_regist);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        fragmentManager = getSupportFragmentManager();

        sale = new SaleDTO();

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        dialogManager = new AlertDialogManager(this);

        showFragment(new SaleRegistFragment(), Boolean.FALSE);
    }

    private void showFragment(Fragment fragment, boolean onStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.sale_activity_frame_layout, fragment);

        if (onStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void selectProduct() {
        progressBar.show();

        productService.findProductsByGrocery(userService.getGroceryDTO(),new ResponseListner<List<ProductDTO>>() {
            @Override
            public void success(List<ProductDTO> response) {
                progressBar.dismiss();
                productsDTO = response;
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
    public List<ProductDTO> getProductsDTO() {
        return Collections.unmodifiableList(productsDTO);
    }

    @Override
    public void selectedProduct(ProductDTO productDTO) {
        progressBar.show();

        stockService.findProductStocksByGroceryAndProduct(userService.getGroceryDTO(), productDTO, new ResponseListner<List<StockDTO>>() {
            @Override
            public void success(List<StockDTO> response) {
                progressBar.dismiss();

                if (response.isEmpty()) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_no_stock_for_the_product_selected), null);
                    return;
                }

                stocksDTO = response;
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
    public List<StockDTO> getStocks() {
        return Collections.unmodifiableList(stocksDTO);
    }

    @Override
    public void selectedStock(StockDTO stockDTO) {
        this.stockDTO = stockDTO;
        showFragment(new AddSaleItemFragment(), Boolean.TRUE);
    }

    @Override
    public StockDTO getStock() {
        return stockDTO;
    }

    @Override
    public void addSaleItem(SaleItemDTO saleItem) {
        sale.addSaleItem(saleItem);
        resetFragment();
    }

    private void resetFragment() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        showFragment(new SaleRegistFragment(), Boolean.FALSE);
    }

    @Override
    public SaleDTO getSale() {
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

        sale.setGrocery(userService.getGroceryDTO());
        saleService.registSale(sale, new ResponseListner<SaleDTO>() {
            @Override
            public void success(SaleDTO response) {
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
