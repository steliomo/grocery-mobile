package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.customer.delegate.CustomerDelegate;
import mz.co.commandline.grocery.customer.fragment.CustomersFragment;
import mz.co.commandline.grocery.customer.fragment.RegistCustomerFragment;
import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.customer.model.CustomersDTO;
import mz.co.commandline.grocery.customer.service.CustomerService;
import mz.co.commandline.grocery.generics.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.generics.dto.ErrorMessage;
import mz.co.commandline.grocery.item.delegate.ItemDelegate;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.main.delegate.MenuDelegate;
import mz.co.commandline.grocery.main.fragment.MenuFragment;
import mz.co.commandline.grocery.menu.Menu;
import mz.co.commandline.grocery.menu.MenuItem;
import mz.co.commandline.grocery.sale.dto.SaleType;
import mz.co.commandline.grocery.sale.fragment.PaymentDetailsFragment;
import mz.co.commandline.grocery.sale.fragment.SalePaymentFragment;
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
import mz.co.commandline.grocery.util.alert.DialogListner;
import mz.co.commandline.grocery.util.alert.DialogManager;
import mz.co.commandline.grocery.util.alert.SaleTypeDialog;

public class SaleActivity extends BaseAuthActivity implements SaleDelegate, SaleableItemDelegate, View.OnClickListener, ItemDelegate, CustomerDelegate, MenuDelegate {

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

    @Inject
    CustomerService customerService;

    private ProgressDialog progressBar;

    private List<SaleableItemDTO> saleableItems;

    private SaleableItemDTO saleableItemDTO;

    private SaleDTO sale;

    private DialogManager dialogManager;

    private ItemType itemType;

    private List<ItemDTO> itemsDTO;

    private SaleTypeDialog saleTypeDialog;

    private CustomersDTO customersDTO;

    private Menu menu;

    private int selectecMenu;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_sale);

        toolbar.setTitle(R.string.sales);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        dialogManager = new AlertDialogManager(this);
        saleTypeDialog = new SaleTypeDialog(this);

        menu = new Menu();
        menu.addMenuItem(new MenuItem(R.string.sale_regist, R.mipmap.ic_sale));
        menu.addMenuItem(new MenuItem(R.string.payment, R.mipmap.ic_payment));

        showFragment(new MenuFragment(), Boolean.FALSE);
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
    public void registInstallmentSale(SaleDTO saleDTO) {
    }

    @Override
    public void registSale() {
        if (sale.getItems().isEmpty()) {
            dialogManager.dialog(AlertType.INFO, getString((R.string.no_item_added)), null);
            return;
        }

        sale.setGrocery(userService.getGroceryDTO());

        saleTypeDialog.dialog(new DialogListner<SaleType>() {
            @Override
            public void perform(SaleType saleType) {
                switch (saleType) {
                    case PART:
                        loadCustomers();
                        break;
                    case DIRECT:
                        directSale();
                        break;
                }
            }
        });
    }

    private void loadCustomers() {
        progressBar.show();
        customerService.findCustomersByUnit(userService.getGroceryDTO().getUuid(), 0, 100, new ResponseListner<CustomersDTO>() {
            @Override
            public void success(CustomersDTO response) {
                progressBar.dismiss();
                customersDTO = response;
                showFragment(new CustomersFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_customers), null);
                Log.e("CUSTOMERS", message);
            }
        });
    }

    private void directSale() {
        progressBar.show();
        saleService.registSale(sale, new ResponseListner<SaleDTO>() {
            @Override
            public void success(SaleDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.sale_registed_with_success), new AlertListner() {
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

                if (itemsDTO.isEmpty()) {
                    dialogManager.dialog(AlertType.INFO, getString(R.string.no_items_available), null);
                    return;
                }

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

    @Override
    public void addCustomer() {
        showFragment(new RegistCustomerFragment(), Boolean.TRUE);
    }

    @Override
    public void registCustomer(CustomerDTO customerDTO) {
        progressBar.show();

        customerDTO.setUnit(userService.getGroceryDTO());
        customerService.registCustomer(customerDTO, new ResponseListner<CustomerDTO>() {
            @Override
            public void success(CustomerDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.user_was_successfully_registed), new AlertListner() {
                    @Override
                    public void perform() {
                        popBackStack();
                        popBackStack();
                        loadCustomers();
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.there_was_an_error_registing_customer), null);
                Log.e("CUSTOMER", message);
            }
        });
    }

    @Override
    public CustomersDTO getCustomersDTO() {
        return customersDTO;
    }

    @Override
    public void selectedCustomer(CustomerDTO customerDTO) {

        if (R.mipmap.ic_payment == selectecMenu) {
            showFragment(new SalePaymentFragment(), Boolean.TRUE);
            return;
        }

        sale.setCustomerDTO(customerDTO);
        showFragment(new PaymentDetailsFragment(), Boolean.TRUE);
    }

    @Override
    public int addBtnVisibility() {
        if (R.mipmap.ic_payment == selectecMenu) {
            return View.GONE;
        }

        return View.VISIBLE;
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return menu.getMenuItems();
    }

    @Override
    public void onClickMenuItem(MenuItem menuItem) {
        switch (menuItem.getIconId()) {
            case R.mipmap.ic_sale:
                selectecMenu = R.mipmap.ic_sale;
                sale = new SaleDTO();
                showFragment(new SaleRegistFragment(), Boolean.TRUE);
                break;
            case R.mipmap.ic_payment:
                selectecMenu = R.mipmap.ic_payment;
                loadCustomers();
                break;
        }

    }

    @Override
    public String getFragmentTitle() {
        return getString(R.string.sales);
    }
}
