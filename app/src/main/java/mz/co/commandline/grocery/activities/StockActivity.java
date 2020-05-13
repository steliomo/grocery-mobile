package mz.co.commandline.grocery.activities;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.product.delegate.ProductDelegate;
import mz.co.commandline.grocery.product.dto.ProductDTO;
import mz.co.commandline.grocery.product.fragment.ProductFragment;
import mz.co.commandline.grocery.product.service.ProductService;
import mz.co.commandline.grocery.stock.delegate.StockDelegate;
import mz.co.commandline.grocery.stock.delegate.UpdateStockDelegate;
import mz.co.commandline.grocery.stock.dto.StockDTO;
import mz.co.commandline.grocery.stock.fragment.StockFragment;
import mz.co.commandline.grocery.stock.fragment.StocksAndPricesFragment;
import mz.co.commandline.grocery.stock.fragment.UpdateStockFragment;
import mz.co.commandline.grocery.stock.service.StockService;
import mz.co.commandline.grocery.util.KeyboardUtil;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

import static mz.co.commandline.grocery.util.FragmentUtil.displayFragment;
import static mz.co.commandline.grocery.util.FragmentUtil.popBackStack;
import static mz.co.commandline.grocery.util.FragmentUtil.resetFragment;

public class StockActivity extends BaseAuthActivity implements View.OnClickListener, UpdateStockDelegate, StockDelegate, ProductDelegate {

    @Inject
    ProductService productService;

    @Inject
    StockService stockService;

    private FragmentManager fragmentManager;

    private ProgressDialog progressBar;

    private AlertDialogManager dialogManager;

    private List<ProductDTO> productsDTO;

    private List<StockDTO> stocksDTO;

    private StockDTO stockDTO;

    private List<StockDTO> updatedStocksAndPrices;

    private Toolbar toolbar;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_stock);

        toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle(R.string.stocks_and_prices);
        toolbar.setNavigationOnClickListener(this);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        fragmentManager = getSupportFragmentManager();
        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        dialogManager = new AlertDialogManager(this);

        updatedStocksAndPrices = new ArrayList<>();

        displayFragment(fragmentManager, R.id.stock_activity_framelayout, new UpdateStockFragment(), Boolean.FALSE);
    }

    @Override
    public void onClick(View view) {
        popBackStack(fragmentManager, this);
    }

    @Override
    public void selectProduct() {
        progressBar.show();

        productService.findProductsByGrocery(userService.getGroceryDTO(), new ResponseListner<List<ProductDTO>>() {
            @Override
            public void success(List<ProductDTO> response) {
                progressBar.dismiss();
                productsDTO = response;
                displayFragment(fragmentManager, R.id.stock_activity_framelayout, new ProductFragment(), Boolean.TRUE);
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
        return productsDTO;
    }

    @Override
    public void selectedProduct(ProductDTO product) {

        KeyboardUtil.hideKeyboard(this, toolbar);

        progressBar.show();

        stockService.findProductStocksByGroceryAndProduct(userService.getGroceryDTO(), product, new ResponseListner<List<StockDTO>>() {
            @Override
            public void success(List<StockDTO> response) {
                progressBar.dismiss();

                if (response.isEmpty()) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_no_stock_for_the_product_selected), null);
                    return;
                }

                stocksDTO = response;
                displayFragment(fragmentManager, R.id.stock_activity_framelayout, new StockFragment(), Boolean.TRUE);
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
        displayFragment(fragmentManager, R.id.stock_activity_framelayout, new StocksAndPricesFragment(), Boolean.TRUE);
    }

    @Override
    public StockDTO getStock() {
        return stockDTO;
    }

    @Override
    public void cancel() {
        resetFragment(fragmentManager);
        displayFragment(fragmentManager, R.id.stock_activity_framelayout, new UpdateStockFragment(), Boolean.TRUE);
    }

    @Override
    public void addStockItem(StockDTO stockDTO) {
        updatedStocksAndPrices.add(stockDTO);
        resetFragment(fragmentManager);
        displayFragment(fragmentManager, R.id.stock_activity_framelayout, new UpdateStockFragment(), Boolean.TRUE);
    }

    @Override
    public void updateStocksAndPrices() {
        progressBar.show();

        if (updatedStocksAndPrices.isEmpty()) {
            progressBar.dismiss();
            dialogManager.dialog(AlertType.ERROR, getString(R.string.add_item_to_update), null);
            return;
        }

        stockService.updateStocksAndPrices(updatedStocksAndPrices, new ResponseListner<Void>() {
            @Override
            public void success(Void responses) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.stock_and_sale_updated_success), new AlertListner() {
                    @Override
                    public void perform() {
                        finish();
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_updating_stock_and_prices), null);
                Log.e("UPDATE_STOCKS", message);
            }
        });
    }

    @Override
    public List<StockDTO> updatedStocksAndPrices() {
        return Collections.unmodifiableList(updatedStocksAndPrices);
    }
}
