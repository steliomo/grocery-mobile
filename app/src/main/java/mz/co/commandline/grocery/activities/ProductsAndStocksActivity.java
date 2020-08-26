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
import mz.co.commandline.grocery.login.delegate.LoginDelegate;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.product.delegate.ProductDelegate;
import mz.co.commandline.grocery.product.dto.ProductDTO;
import mz.co.commandline.grocery.product.fragment.ProductFragment;
import mz.co.commandline.grocery.product.service.ProductService;
import mz.co.commandline.grocery.stock.delegate.ProductsAndStocksDelegate;
import mz.co.commandline.grocery.stock.delegate.StockDelegate;
import mz.co.commandline.grocery.stock.delegate.UpdateStockDelegate;
import mz.co.commandline.grocery.stock.dto.ActionType;
import mz.co.commandline.grocery.stock.dto.StockDTO;
import mz.co.commandline.grocery.stock.fragment.ProductsAndStocksFragment;
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

public class ProductsAndStocksActivity extends BaseAuthActivity implements View.OnClickListener, UpdateStockDelegate, StockDelegate, ProductsAndStocksDelegate, ProductDelegate {

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

    private List<StockDTO> stocks;

    private Toolbar toolbar;

    private String title;

    private ActionType actionType;

    private String actionName;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_products_and_stocks);

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

        stocks = new ArrayList<>();

        displayFragment(fragmentManager, R.id.products_and_stocks_activity_framelayout, new ProductsAndStocksFragment(), Boolean.FALSE);
    }

    @Override
    public void onClick(View view) {
        popBackStack(fragmentManager, this);
    }

    @Override
    public void selectProduct() {
        progressBar.show();

        if (ActionType.ADD_PRODUCTS.equals(actionType)) {

            productService.findProductsNotInThisGrocery(userService.getGroceryDTO(), new ResponseListner<List<ProductDTO>>() {
                @Override
                public void success(List<ProductDTO> response) {
                    progressBar.dismiss();
                    productsDTO = response;
                    displayFragment(fragmentManager, R.id.products_and_stocks_activity_framelayout, new ProductFragment(), Boolean.TRUE);
                }

                @Override
                public void error(String message) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_error_on_add_item), null);
                    progressBar.dismiss();
                    Log.e("PRODUCTS", message);
                }
            });

            return;
        }

        productService.findProductsByGrocery(userService.getGroceryDTO(), new ResponseListner<List<ProductDTO>>() {
            @Override
            public void success(List<ProductDTO> response) {
                progressBar.dismiss();

                if (response.isEmpty()) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.grocery_without_products), null);
                    return;
                }

                productsDTO = response;
                displayFragment(fragmentManager, R.id.products_and_stocks_activity_framelayout, new ProductFragment(), Boolean.TRUE);
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

        if (ActionType.ADD_PRODUCTS.equals(actionType)) {
            stockService.findProductStocksNotInThisGroceryByProduct(userService.getGroceryDTO(), product, new ResponseListner<List<StockDTO>>() {
                @Override
                public void success(List<StockDTO> response) {
                    progressBar.dismiss();

                    stocksDTO = response;
                    displayFragment(fragmentManager, R.id.products_and_stocks_activity_framelayout, new StockFragment(), Boolean.TRUE);
                }

                @Override
                public void error(String message) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_error_on_selecting_product), null);
                    Log.e("STOCKS", message);
                }
            });

            return;
        }

        stockService.findProductStocksByGroceryAndProduct(userService.getGroceryDTO(), product, new ResponseListner<List<StockDTO>>() {
            @Override
            public void success(List<StockDTO> response) {
                progressBar.dismiss();

                if (response.isEmpty()) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_no_stock_for_the_product_selected), null);
                    return;
                }

                stocksDTO = response;
                displayFragment(fragmentManager, R.id.products_and_stocks_activity_framelayout, new StockFragment(), Boolean.TRUE);
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

        if (ActionType.ADD_PRODUCTS.equals(actionType)) {
            this.stockDTO.setGroceryDTO(userService.getGroceryDTO());
            this.stockDTO.setPurchasePrice("");
            this.stockDTO.setSalePrice("");
            this.stockDTO.setQuantity("");
            this.stockDTO.setMinimumStock("");

            this.stockDTO.setId(null);
            this.stockDTO.setUuid(null);
        }

        displayFragment(fragmentManager, R.id.products_and_stocks_activity_framelayout, new StocksAndPricesFragment(), Boolean.TRUE);
    }

    @Override
    public StockDTO getStock() {
        return stockDTO;
    }

    @Override
    public void addNewProducts() {
        this.actionType = ActionType.ADD_PRODUCTS;
        this.title = getString(R.string.add_new_products);
        this.actionName = getString(R.string.add);
        displayFragment(fragmentManager, R.id.products_and_stocks_activity_framelayout, new UpdateStockFragment(), Boolean.TRUE);
    }

    @Override
    public void updateStockAndPrices() {
        this.actionType = ActionType.UPDATE_STOCKS;
        this.title = getString(R.string.update_stocks_and_prices);
        this.actionName = getString(R.string.update);
        displayFragment(fragmentManager, R.id.products_and_stocks_activity_framelayout, new UpdateStockFragment(), Boolean.TRUE);
    }

    @Override
    public String getTiTle() {
        return title;
    }

    @Override
    public String getActionName() {
        return actionName;
    }

    @Override
    public void cancel() {
        resetFragment(fragmentManager);
        displayFragment(fragmentManager, R.id.products_and_stocks_activity_framelayout, new ProductsAndStocksFragment(), Boolean.FALSE);
    }

    @Override
    public void addStockItem(StockDTO stockDTO) {
        stocks.add(stockDTO);
        resetFragment(fragmentManager);
        displayFragment(fragmentManager, R.id.products_and_stocks_activity_framelayout, new UpdateStockFragment(), Boolean.TRUE);
    }

    @Override
    public void updateStocksAndPrices() {
        progressBar.show();

        if (stocks.isEmpty()) {
            progressBar.dismiss();
            dialogManager.dialog(AlertType.ERROR, getString(R.string.add_item_to_update), null);
            return;
        }

        if (ActionType.ADD_PRODUCTS.equals(actionType)) {
            stockService.addStockProducts(stocks, new ResponseListner<Void>() {
                @Override
                public void success(Void response) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.SUCCESS, getString(R.string.new_products_added_with_success), new AlertListner() {
                        @Override
                        public void perform() {
                            finish();
                        }
                    });
                }

                @Override
                public void error(String message) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_adding_products), new AlertListner() {
                        @Override
                        public void perform() {
                            finish();
                        }
                    });
                    Log.e("ADD_PRODUCTS", message);
                }
            });

            return;
        }

        stockService.updateStocksAndPrices(stocks, new ResponseListner<Void>() {
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
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_updating_stock_and_prices), new AlertListner() {
                    @Override
                    public void perform() {
                        finish();
                    }
                });
                Log.e("UPDATE_STOCKS", message);
            }
        });
    }

    public List<StockDTO> updatedStocksAndPrices() {
        return Collections.unmodifiableList(stocks);
    }
}
