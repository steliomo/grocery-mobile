package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
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
import mz.co.commandline.grocery.sale.delegate.SaleDelegate;
import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SaleItemDTO;
import mz.co.commandline.grocery.sale.fragment.AddSaleItemFragment;
import mz.co.commandline.grocery.sale.fragment.ItemTypeFragment;
import mz.co.commandline.grocery.sale.fragment.SaleRegistFragment;
import mz.co.commandline.grocery.sale.service.SaleService;
import mz.co.commandline.grocery.saleable.delegate.SaleableItemDelegate;
import mz.co.commandline.grocery.saleable.fragment.StockFragment;
import mz.co.commandline.grocery.saleable.service.StockService;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.KeyboardUtil;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

public class SaleActivity extends BaseAuthActivity implements SaleDelegate, SaleableItemDelegate, View.OnClickListener, ItemDelegate {

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

    @Inject
    ItemService itemService;

    @Inject
    SaleableItemService saleableItemService;

    private ProgressDialog progressBar;

    private List<SaleableItemDTO> saleableItems;

    private SaleableItemDTO saleableItemDTO;

    private SaleDTO sale;

    private AlertDialogManager dialogManager;

    private ItemType itemType;

    private List<ItemDTO> itemsDTO;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_sale);

        toolbar.setTitle(R.string.sale_regist);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        sale = new SaleDTO();

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        dialogManager = new AlertDialogManager(this);

        showFragment(new SaleRegistFragment(), Boolean.FALSE);
    }

    @Override
    public void addSaleItem(SaleItemDTO saleItem) {
        sale.addSaleItem(saleItem);
        resetFragment();
        showFragment(new SaleRegistFragment(), Boolean.FALSE);
    }

    @Override
    public SaleDTO getSale() {
        return sale;
    }

    @Override
    public void cancel() {
        resetFragment();
        showFragment(new SaleRegistFragment(), Boolean.FALSE);
    }

    @Override
    public void selectItem() {
        showFragment(new ItemTypeFragment(), Boolean.TRUE);
    }

    @Override
    public void registSale() {
        sale.setGrocery(userService.getGroceryDTO());

        progressBar.show();

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

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("SALE_B", errorMessage.getDeveloperMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        popBackStack();
    }

    @Override
    public void selectItemType(ItemType itemType) {
        this.itemType = itemType;
        progressBar.show();

        itemService.findItemByUnit(itemType, userService.getGroceryDTO(), new ResponseListner<List<ItemDTO>>() {
            @Override
            public void success(List<ItemDTO> response) {
                progressBar.dismiss();
                itemsDTO = response;
                showFragment(new ProductFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_error_on_add_item), null);
                progressBar.dismiss();
                Log.e("ITEMS", message);
            }
        });
    }

    @Override
    public List<ItemDTO> getItemsDTO() {
        return itemsDTO;
    }

    @Override
    public void selectedItem(ItemDTO itemDTO) {

        KeyboardUtil.hideKeyboard(this, toolbar);
        progressBar.show();

        saleableItemService.findSalebleItemByItemAndUnit(itemDTO, userService.getGroceryDTO(), new ResponseListner<List<SaleableItemDTO>>() {
            @Override
            public void success(List<SaleableItemDTO> response) {
                progressBar.dismiss();

                if (response.isEmpty()) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_no_stock_for_the_product_selected), null);
                    return;
                }

                saleableItems = response;
                showFragment(new StockFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_error_on_selecting_product), null);
                Log.e("SALABLE_ITEM", message);
            }
        });
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public List<SaleableItemDTO> getSaleableItems() {
        return Collections.unmodifiableList(saleableItems);
    }

    @Override
    public void selectedSaleableItem(SaleableItemDTO saleableItemDTO) {
        this.saleableItemDTO = saleableItemDTO;
        showFragment(new AddSaleItemFragment(), Boolean.TRUE);
    }

    @Override
    public SaleableItemDTO getSaleableItem() {
        return saleableItemDTO;
    }

    @Override
    public int getActivityFrameLayoutId() {
        return R.id.sale_activity_frame_layout;
    }
}
