package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.generics.dto.ErrorMessage;
import mz.co.commandline.grocery.inventory.delegate.InventoryDelegate;
import mz.co.commandline.grocery.inventory.dto.InventoryDTO;
import mz.co.commandline.grocery.inventory.dto.InventoryStatus;
import mz.co.commandline.grocery.inventory.dto.StockInventoryDTO;
import mz.co.commandline.grocery.inventory.fragment.AddInventoryItemFragment;
import mz.co.commandline.grocery.inventory.fragment.ApproveInventoryFragment;
import mz.co.commandline.grocery.inventory.fragment.PerformInventoryFragment;
import mz.co.commandline.grocery.inventory.fragment.StockAnalysisDetailFragment;
import mz.co.commandline.grocery.inventory.fragment.StockAnalysisFragment;
import mz.co.commandline.grocery.inventory.service.InventoryService;
import mz.co.commandline.grocery.item.delegate.ItemDelegate;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.main.delegate.MenuDelegate;
import mz.co.commandline.grocery.main.fragment.MenuFragment;
import mz.co.commandline.grocery.menu.Menu;
import mz.co.commandline.grocery.menu.MenuItem;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.item.service.ItemService;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.item.dto.ProductDTO;
import mz.co.commandline.grocery.item.fragment.ProductFragment;
import mz.co.commandline.grocery.saleable.dto.StockDTO;
import mz.co.commandline.grocery.saleable.fragment.StockFragment;
import mz.co.commandline.grocery.saleable.service.StockService;
import mz.co.commandline.grocery.user.dto.UserRole;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.DateUtil;
import mz.co.commandline.grocery.util.KeyboardUtil;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

public class InventoryActivity extends BaseAuthActivity implements View.OnClickListener, InventoryDelegate, ItemDelegate, MenuDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ItemService itemService;

    @Inject
    StockService stockService;

    @Inject
    InventoryService inventoryService;

    @Inject
    UserService userService;

    private ProgressDialog progressBar;

    private AlertDialogManager dialogManager;

    private List<ItemDTO> productsDTO;

    private List<StockDTO> stocksDTO;

    private SaleableItemDTO stock;

    private InventoryDTO inventory;

    private Menu menu;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_inventory);

        toolbar.setTitle(R.string.inventory);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        dialogManager = new AlertDialogManager(this);

        menu = new Menu();
        menu.addMenuItem(new MenuItem(R.string.perform_inventory, R.mipmap.ic_perform_inventory));

        if (!UserRole.OPERATOR.equals(userService.getGroceryUser().getUserRole())) {
            menu.addMenuItem(new MenuItem(R.string.approve_inventory, R.mipmap.ic_approved_stock));
            menu.addMenuItem(new MenuItem(R.string.item_track, R.mipmap.ic_item_track));
        }

        showFragment(new MenuFragment(), Boolean.FALSE);
    }

    @Override
    public void onClick(View view) {
        popBackStack();
    }

    public void displayPerformInventoryFragment() {
        progressBar.show();

        inventoryService.findInventoryByGroceryAndStatus(userService.getGroceryDTO(), InventoryStatus.PENDING, new ResponseListner<InventoryDTO>() {
            @Override
            public void success(InventoryDTO response) {
                progressBar.dismiss();
                inventory = response;
                showFragment(new PerformInventoryFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_performing_inventory), null);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                inventory = new InventoryDTO(userService.getGroceryDTO(), DateUtil.format(new Date(), DateUtil.NORMAL_PATTERN), InventoryStatus.PENDING);
                showFragment(new PerformInventoryFragment(), Boolean.TRUE);
            }
        });
    }

    public void displayApproveInventoryFragment() {
        progressBar.show();

        inventoryService.findInventoryByGroceryAndStatus(userService.getGroceryDTO(), InventoryStatus.PENDING, new ResponseListner<InventoryDTO>() {
            @Override
            public void success(InventoryDTO response) {
                progressBar.dismiss();
                inventory = response;
                showFragment(new ApproveInventoryFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_performing_inventory), null);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.no_inventory_to_approve), null);
            }
        });
    }

    @Override
    public InventoryDTO getInventoryDTO() {
        return this.inventory;
    }

    @Override
    public void addStockInventoryDTO(StockInventoryDTO stockInventoryDTO) {
        inventory.addStockInventoryDTO(stockInventoryDTO);
        resetFragment();
        showFragment(new PerformInventoryFragment(), Boolean.TRUE);
    }

    @Override
    public void cancel() {
        resetFragment();
        showFragment(new PerformInventoryFragment(), Boolean.TRUE);
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
                        resetFragment();
                        showFragment(new MenuFragment(), Boolean.FALSE);
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_performing_inventory), null);
                Log.e("INVENTORY", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("INVENTORY_B", errorMessage.getDeveloperMessage());
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
                        resetFragment();
                        showFragment(new MenuFragment(), Boolean.FALSE);
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_approving_inventory), null);
                Log.e("INVENTORY", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("INVENTORY_B", errorMessage.getDeveloperMessage());

            }
        });
    }

    @Override
    public List<StockDTO> getStocksDTO() {
        return stocksDTO;
    }

    @Override
    public void stockAnalysisDtails(StockDTO stockDTO) {
        this.stock = stockDTO;
        showFragment(new StockAnalysisDetailFragment(), Boolean.TRUE);
    }

    @Override
    public void regularizeStock(@NotNull StockDTO stockDTO) {
        progressBar.show();

        stockService.regularizeStock(stockDTO, new ResponseListner<StockDTO>() {
            @Override
            public void success(StockDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.stock_analysis_reguralized_success), new AlertListner() {
                    @Override
                    public void perform() {
                        resetFragment();
                        showFragment(new MenuFragment(), Boolean.FALSE);
                    }
                });
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("REGULARIZE_B", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_regularizing_stock), null);
                Log.e("REGULARIZE_B", message);
            }
        });
    }

    @Override
    public void selectItemType(ItemType itemType) {
        progressBar.show();

        itemService.findItemByUnit(itemType, userService.getGroceryDTO(), new ResponseListner<List<ItemDTO>>() {
            @Override
            public void success(List<ItemDTO> response) {
                progressBar.dismiss();
                productsDTO = new ArrayList<>(response);
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
    public List<ItemDTO> getItemsDTO() {
        return new ArrayList<>(productsDTO);
    }

    @Override
    public void selectedItem(ItemDTO itemDTO) {
        KeyboardUtil.hideKeyboard(this, toolbar);
        progressBar.show();

        ProductDTO productDTO = (ProductDTO) itemDTO;

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
    public List<SaleableItemDTO> getSaleableItems() {
        return new ArrayList<SaleableItemDTO>(stocksDTO);
    }

    @Override
    public void selectedSaleableItem(SaleableItemDTO saleableItemDTO) {
        this.stock = saleableItemDTO;
        showFragment(new AddInventoryItemFragment(), Boolean.TRUE);
    }

    @Override
    public SaleableItemDTO getSaleableItem() {
        return stock;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.PRODUCT;
    }

    @Override
    public int getActivityFrameLayoutId() {
        return R.id.inventory_activity_framelayout;
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return menu.getMenuItems();
    }

    @Override
    public void onClickMenuItem(MenuItem menuItem) {
        switch (menuItem.getIconId()) {
            case R.mipmap.ic_perform_inventory:
                displayPerformInventoryFragment();
                break;

            case R.mipmap.ic_approved_stock:
                displayApproveInventoryFragment();
                break;

            case R.mipmap.ic_item_track:

                stockInAnalysisDisplay();
                break;
        }
    }

    private void stockInAnalysisDisplay() {
        progressBar.show();
        stockService.findStocksInAnalysis(userService.getGroceryDTO().getUuid(), new ResponseListner<List<StockDTO>>() {
            @Override
            public void success(List<StockDTO> response) {
                progressBar.dismiss();
                if (response.isEmpty()) {
                    dialogManager.dialog(AlertType.INFO, getString(R.string.unit_without_stocks_in_analysis), null);
                    return;
                }

                stocksDTO = response;
                showFragment(new StockAnalysisFragment(), Boolean.TRUE);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("STOCKS_ANALYSIS_B", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_stock_in_analysis), null);
                Log.e("STOCKS_ANALYSIS", message);
            }
        });
    }

    @Override
    public String getFragmentTitle() {
        return getString(R.string.inventory);
    }
}
