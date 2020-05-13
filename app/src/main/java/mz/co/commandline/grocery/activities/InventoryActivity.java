package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.inventory.delegate.InventoryDelegate;
import mz.co.commandline.grocery.inventory.dto.InventoryDTO;
import mz.co.commandline.grocery.inventory.dto.InventoryStatus;
import mz.co.commandline.grocery.inventory.dto.StockInventoryDTO;
import mz.co.commandline.grocery.inventory.fragment.AddInventoryItemFragment;
import mz.co.commandline.grocery.inventory.fragment.ApproveInventoryFragment;
import mz.co.commandline.grocery.inventory.fragment.InventoryMenuFragment;
import mz.co.commandline.grocery.inventory.fragment.PerformInventoryFragment;
import mz.co.commandline.grocery.inventory.service.InventoryService;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.product.delegate.ProductDelegate;
import mz.co.commandline.grocery.product.dto.ProductDTO;
import mz.co.commandline.grocery.product.fragment.ProductFragment;
import mz.co.commandline.grocery.product.service.ProductService;
import mz.co.commandline.grocery.stock.delegate.StockDelegate;
import mz.co.commandline.grocery.stock.dto.StockDTO;
import mz.co.commandline.grocery.stock.fragment.StockFragment;
import mz.co.commandline.grocery.stock.service.StockService;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.DateUtil;
import mz.co.commandline.grocery.util.KeyboardUtil;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

import static mz.co.commandline.grocery.util.FragmentUtil.displayFragment;
import static mz.co.commandline.grocery.util.FragmentUtil.resetFragment;
import static mz.co.commandline.grocery.util.FragmentUtil.popBackStack;

public class InventoryActivity extends BaseAuthActivity implements View.OnClickListener, InventoryDelegate, ProductDelegate, StockDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ProductService productService;

    @Inject
    StockService stockService;

    @Inject
    InventoryService inventoryService;

    @Inject
    UserService userService;

    private FragmentManager fragmentManager;

    private ProgressDialog progressBar;

    private AlertDialogManager dialogManager;

    private List<ProductDTO> productsDTO;

    private List<StockDTO> stocksDTO;

    private StockDTO stock;

    private InventoryDTO inventory;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_inventory);

        toolbar.setTitle(R.string.inventory);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        fragmentManager = getSupportFragmentManager();

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        dialogManager = new AlertDialogManager(this);

        displayFragment(fragmentManager, R.id.inventory_activity_framelayout, new InventoryMenuFragment(), Boolean.FALSE);
    }

    @Override
    public void onClick(View view) {
        popBackStack(fragmentManager, this);
    }

    @Override
    public void displayPerformInventoryFragment() {
        progressBar.show();

        inventoryService.findInventoryByGroceryAndStatus(userService.getGroceryDTO(), InventoryStatus.PENDING, new ResponseListner<InventoryDTO>() {
            @Override
            public void success(InventoryDTO response) {
                progressBar.dismiss();
                inventory = response;
                displayFragment(fragmentManager, R.id.inventory_activity_framelayout, new PerformInventoryFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();

                if (message.contains(getString(R.string.inventory_not_found))) {
                    inventory = new InventoryDTO(userService.getGroceryDTO(), DateUtil.format(new Date(), DateUtil.NORMAL_PATTERN), InventoryStatus.PENDING);
                    displayFragment(fragmentManager, R.id.inventory_activity_framelayout, new PerformInventoryFragment(), Boolean.TRUE);
                    return;
                }

                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_performing_inventory), null);
            }
        });
    }

    @Override
    public void displayApproveInventoryFragment() {
        progressBar.show();

        inventoryService.findInventoryByGroceryAndStatus(userService.getGroceryDTO(), InventoryStatus.PENDING, new ResponseListner<InventoryDTO>() {
            @Override
            public void success(InventoryDTO response) {
                progressBar.dismiss();
                inventory = response;
                displayFragment(fragmentManager, R.id.inventory_activity_framelayout, new ApproveInventoryFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();

                if (message.contains(getString(R.string.inventory_not_found))) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.no_inventory_to_approve), null);
                    return;
                }

                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_performing_inventory), null);
            }
        });
    }

    @Override
    public InventoryDTO getInventoryDTO() {
        return this.inventory;
    }

    @Override
    public void selectProduct() {
        progressBar.show();

        productService.findProductsByGrocery(userService.getGroceryDTO(), new ResponseListner<List<ProductDTO>>() {
            @Override
            public void success(List<ProductDTO> response) {
                progressBar.dismiss();
                productsDTO = response;
                displayFragment(fragmentManager, R.id.inventory_activity_framelayout, new ProductFragment(), Boolean.TRUE);
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
    public void selectedProduct(ProductDTO productDTO) {

        KeyboardUtil.hideKeyboard(this, toolbar);
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
                displayFragment(fragmentManager, R.id.inventory_activity_framelayout, new StockFragment(), Boolean.TRUE);
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
        this.stock = stockDTO;
        displayFragment(fragmentManager, R.id.inventory_activity_framelayout, new AddInventoryItemFragment(), Boolean.TRUE);
    }

    @Override
    public StockDTO getStock() {
        return stock;
    }

    @Override
    public void addStockInventoryDTO(StockInventoryDTO stockInventoryDTO) {
        inventory.addStockInventoryDTO(stockInventoryDTO);
        resetFragment(fragmentManager);
        displayFragment(fragmentManager, R.id.inventory_activity_framelayout, new PerformInventoryFragment(), Boolean.TRUE);
    }

    @Override
    public void cancel() {
        resetFragment(fragmentManager);
        displayFragment(fragmentManager, R.id.inventory_activity_framelayout, new PerformInventoryFragment(), Boolean.TRUE);
    }

    @Override
    public void performInventory() {
        progressBar.show();

        inventoryService.performInventory(inventory, new ResponseListner<InventoryDTO>() {
            @Override
            public void success(InventoryDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.inventory_was_performed_with_success), new AlertListner() {
                    @Override
                    public void perform() {
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        displayFragment(fragmentManager, R.id.inventory_activity_framelayout, new InventoryMenuFragment(), Boolean.FALSE);
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();

                if (message.contains("Cannot perform inventory without stock items...")) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.cannot_perform_inventory_without_items), null);
                    return;
                }

                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_performing_inventory), null);
                Log.e("INVENTORY", message);
            }
        });
    }

    @Override
    public void approveInventory() {
        progressBar.show();

        inventoryService.approveInventory(inventory, new ResponseListner<InventoryDTO>() {
            @Override
            public void success(InventoryDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.inventory_approved_with_success), new AlertListner() {
                    @Override
                    public void perform() {
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        displayFragment(fragmentManager, R.id.inventory_activity_framelayout, new InventoryMenuFragment(), Boolean.FALSE);
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_approving_inventory), null);
                Log.e("INVENTORY", message);
            }
        });
    }
}
