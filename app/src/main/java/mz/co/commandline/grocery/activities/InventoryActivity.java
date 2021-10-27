package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

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
import mz.co.commandline.grocery.inventory.fragment.InventoryMenuFragment;
import mz.co.commandline.grocery.inventory.fragment.PerformInventoryFragment;
import mz.co.commandline.grocery.inventory.service.InventoryService;
import mz.co.commandline.grocery.item.delegate.ItemDelegate;
import mz.co.commandline.grocery.item.dto.ItemDTO;
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
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.DateUtil;
import mz.co.commandline.grocery.util.KeyboardUtil;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

public class InventoryActivity extends BaseAuthActivity implements View.OnClickListener, InventoryDelegate, ItemDelegate {

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

        showFragment(new InventoryMenuFragment(), Boolean.FALSE);
    }

    @Override
    public void onClick(View view) {
        popBackStack();
    }

    @Override
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

    @Override
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
                        popBackStack();
                        showFragment(new InventoryMenuFragment(), Boolean.FALSE);
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
                        popBackStack();
                        showFragment(new InventoryMenuFragment(), Boolean.FALSE);
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
}
