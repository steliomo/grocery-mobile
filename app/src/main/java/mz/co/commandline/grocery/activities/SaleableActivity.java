package mz.co.commandline.grocery.activities;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.generics.dto.ErrorMessage;
import mz.co.commandline.grocery.item.delegate.ItemDelegate;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.item.service.ItemService;
import mz.co.commandline.grocery.saleable.service.SaleableItemService;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.item.fragment.ProductFragment;
import mz.co.commandline.grocery.item.service.ProductService;
import mz.co.commandline.grocery.sale.fragment.ItemTypeFragment;
import mz.co.commandline.grocery.saleable.dto.SaleableDTO;
import mz.co.commandline.grocery.saleable.fragment.SaleableServiceFragment;
import mz.co.commandline.grocery.saleable.service.SaleableService;
import mz.co.commandline.grocery.saleable.delegate.SaleableItemActionDelegate;
import mz.co.commandline.grocery.saleable.dto.ActionType;
import mz.co.commandline.grocery.saleable.fragment.ProductsAndStocksFragment;
import mz.co.commandline.grocery.saleable.fragment.StockFragment;
import mz.co.commandline.grocery.saleable.fragment.StocksAndPricesFragment;
import mz.co.commandline.grocery.saleable.fragment.UpdateStockFragment;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

public class SaleableActivity extends BaseAuthActivity implements View.OnClickListener, SaleableItemActionDelegate, ItemDelegate {

    @Inject
    ProductService productService;

    @Inject
    ItemService itemService;

    @Inject
    SaleableItemService saleableItemService;

    @Inject
    SaleableService saleableService;

    private ProgressDialog progressBar;

    private AlertDialogManager dialogManager;

    private Toolbar toolbar;

    private String title;

    private ActionType actionType;

    private String actionName;

    private ItemType itemType;

    private List<ItemDTO> items;

    private List<SaleableItemDTO> saleableItems;

    private SaleableItemDTO saleableItem;

    private SaleableDTO saleable;


    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_products_and_stocks);

        toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle(R.string.stocks_and_prices);
        toolbar.setNavigationOnClickListener(this);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        dialogManager = new AlertDialogManager(this);

        saleableItems = new ArrayList<>();

        showFragment(new ProductsAndStocksFragment(), Boolean.FALSE);
    }

    @Override
    public void onClick(View view) {
        popBackStack();
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
        resetFragment();
        showFragment(new ProductsAndStocksFragment(), Boolean.FALSE);
    }

    @Override
    public void selectItem() {
        showFragment(new ItemTypeFragment(), Boolean.TRUE);
    }

    @Override
    public SaleableDTO getSaleable() {
        return saleable;
    }

    @Override
    public void selectedAction(ActionType actionType) {
        this.actionType = actionType;

        if (ActionType.ADD.equals(actionType)) {
            this.title = getString(R.string.add_new_products);
            this.actionName = getString(R.string.add);

            showFragment(new UpdateStockFragment(), Boolean.TRUE);

            return;
        }

        this.title = getString(R.string.update_stocks_and_services);
        this.actionName = getString(R.string.update);

        showFragment(new UpdateStockFragment(), Boolean.TRUE);
    }

    @Override
    public void addSaleableItem(SaleableItemDTO saleableItem) {
        if (ActionType.ADD.equals(actionType)) {
            saleable.addNewSaleableItem(saleableItem);
            resetFragment();
            showFragment(new ProductsAndStocksFragment(), Boolean.FALSE);
            selectedAction(actionType);
            return;
        }

        saleable.updateSaleableItem(saleableItem);
        resetFragment();
        showFragment(new ProductsAndStocksFragment(), Boolean.FALSE);
        selectedAction(actionType);
    }

    @Override
    public void execute() {

        if (saleable.getSaleableItems().isEmpty()) {
            dialogManager.dialog(AlertType.ERROR, getString(R.string.saleable_without_items_not_allowed), null);
            return;
        }

        progressBar.show();

        if (ActionType.ADD.equals(actionType)) {
            saleableService.addSaleable(saleable, new ResponseListner<SaleableDTO>() {
                @Override
                public void success(SaleableDTO response) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.SUCCESS, getString(R.string.saleable_items_created), new AlertListner() {
                        @Override
                        public void perform() {
                            cancel();
                        }
                    });
                }

                @Override
                public void error(String message) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.saleable_items_creation_error), null);
                    Log.e("SALEABLE ", message);
                }
            });

            return;
        }

        saleableService.updateSaleable(saleable, new ResponseListner<SaleableDTO>() {
            @Override
            public void success(SaleableDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.saleable_items_updated), new AlertListner() {
                    @Override
                    public void perform() {
                        cancel();
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.saleable_items_update_error), null);
                Log.e("SALEABLE ", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("SALEABLE_B ", errorMessage.getDeveloperMessage());
            }
        });
    }

    @Override
    public void prepareSaleable() {
        saleable = new SaleableDTO();
        saleable.setUnit(userService.getGroceryDTO());
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public int getActivityFrameLayoutId() {
        return R.id.products_and_stocks_activity_framelayout;
    }

    @Override
    public void selectItemType(ItemType itemType) {
        this.itemType = itemType;
        progressBar.show();

        if (ActionType.ADD.equals(actionType)) {
            itemService.findItemsNotInThisUnit(itemType, userService.getGroceryDTO(), new ResponseListner<List<ItemDTO>>() {
                @Override
                public void success(List<ItemDTO> response) {
                    progressBar.dismiss();
                    items = response;

                    if (items.isEmpty()) {
                        dialogManager.dialog(AlertType.INFO, getString(R.string.no_items_available), null);
                        return;
                    }

                    showFragment(new ProductFragment(), Boolean.TRUE);
                }

                @Override
                public void error(String message) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.error_selecting_item), null);
                    progressBar.dismiss();
                    Log.e("ITEMS", message);
                }
            });

            return;
        }

        itemService.findItemByUnit(itemType, userService.getGroceryDTO(), new ResponseListner<List<ItemDTO>>() {
            @Override
            public void success(List<ItemDTO> response) {
                progressBar.dismiss();
                items = response;

                if (items.isEmpty()) {
                    dialogManager.dialog(AlertType.INFO, getString(R.string.no_items_available), null);
                    return;
                }

                showFragment(new ProductFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_selecting_item), null);
                progressBar.dismiss();
                Log.e("ITEMS", message);
            }
        });
    }

    @Override
    public List<ItemDTO> getItemsDTO() {
        return items;
    }

    @Override
    public void selectedItem(ItemDTO itemDTO) {
        progressBar.show();

        if (ActionType.ADD.equals(actionType)) {
            saleableItemService.findSaleableItemsNotInThisUnitByItem(itemDTO, userService.getGroceryDTO(), new ResponseListner<List<SaleableItemDTO>>() {
                @Override
                public void success(List<SaleableItemDTO> response) {
                    progressBar.dismiss();
                    saleableItems = response;

                    showFragment(new StockFragment(), Boolean.TRUE);
                }

                @Override
                public void error(String message) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.ERROR, "", null);
                    Log.e("SALABLE_ITEM", message);
                }
            });

            return;
        }

        saleableItemService.findSalebleItemByItemAndUnit(itemDTO, userService.getGroceryDTO(), new ResponseListner<List<SaleableItemDTO>>() {
            @Override
            public void success(List<SaleableItemDTO> response) {
                progressBar.dismiss();
                saleableItems = response;

                showFragment(new StockFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, "", null);
                Log.e("SALABLE_ITEM", message);
            }
        });
    }

    @Override
    public List<SaleableItemDTO> getSaleableItems() {
        return Collections.unmodifiableList(saleableItems);
    }

    @Override
    public void selectedSaleableItem(SaleableItemDTO saleableItemDTO) {
        this.saleableItem = saleableItemDTO;

        if (ItemType.PRODUCT.equals(saleableItem.getSalableItemType())) {

            if (ActionType.ADD.equals(actionType)) {
                title = getString(R.string.add_stocks);
            } else {
                title = getString(R.string.update_stocks);
            }

            showFragment(new StocksAndPricesFragment(), Boolean.TRUE);
            return;
        }

        if (ActionType.ADD.equals(actionType)) {
            title = getString(R.string.add_service);
        } else {
            title = getString(R.string.update_service);
        }

        showFragment(new SaleableServiceFragment(), Boolean.TRUE);
    }

    @Override
    public SaleableItemDTO getSaleableItem() {
        return saleableItem;
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }
}
