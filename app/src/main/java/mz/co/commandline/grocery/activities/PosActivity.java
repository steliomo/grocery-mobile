package mz.co.commandline.grocery.activities;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.generics.dto.ErrorMessage;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.item.fragment.ProductFragment;
import mz.co.commandline.grocery.item.service.ItemService;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.pos.delegate.PosDelegate;
import mz.co.commandline.grocery.pos.fragment.OpenTableFragment;
import mz.co.commandline.grocery.pos.fragment.PosAddOrderItemFragment;
import mz.co.commandline.grocery.pos.fragment.PosAddOrdersFragment;
import mz.co.commandline.grocery.pos.fragment.PosBillFragment;
import mz.co.commandline.grocery.pos.fragment.PosFragment;
import mz.co.commandline.grocery.pos.fragment.PosPaymentFragment;
import mz.co.commandline.grocery.pos.fragment.TableDetailsFragment;
import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.sale.dto.SaleItemDTO;
import mz.co.commandline.grocery.sale.dto.SalePaymentDTO;
import mz.co.commandline.grocery.sale.dto.SalesDTO;
import mz.co.commandline.grocery.sale.fragment.ItemTypeFragment;
import mz.co.commandline.grocery.sale.service.SaleService;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.saleable.fragment.StockFragment;
import mz.co.commandline.grocery.saleable.service.SaleableItemService;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.KeyboardUtil;
import mz.co.commandline.grocery.util.SalePrinter;
import mz.co.commandline.grocery.util.SalePrinterImpl;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;
import mz.co.commandline.grocery.util.alert.DialogManager;
import mz.co.commandline.grocery.util.alert.OptionDialog;

public class PosActivity extends BaseAuthActivity implements View.OnClickListener, PosDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    SaleService saleService;

    @Inject
    UserService userService;

    @Inject
    ItemService itemService;

    @Inject
    SaleableItemService saleableItemService;

    private OptionDialog optionDialog;

    private List<SaleDTO> tables;

    private SaleDTO table;

    private ItemType itemType;

    private List<ItemDTO> items;

    private List<SaleableItemDTO> saleableItems;

    private SaleableItemDTO saleableItem;

    private SalePrinter salePrinter;

    @Override
    public int getActivityFrameLayoutId() {
        return R.id.pos_activity_frame_layout;
    }

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_pos);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        toolbar.setTitle(R.string.sales);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        optionDialog = new OptionDialog(this);

        loadOpenedTables();
    }

    private void loadOpenedTables() {
        progressBar.show();
        saleService.fetchOpenedTables(userService.getUnitDTO().getUuid(), new ResponseListner<SalesDTO>() {
            @Override
            public void success(SalesDTO response) {
                progressBar.dismiss();
                tables = response.getSalesDTO();
                showFragment(new PosFragment(), Boolean.FALSE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.there_was_an_error_loading_tables), null);
                Log.e("POS_LOADING_TABLES", message);
            }
        });
    }

    @Override
    public void onClick(View view) {
        popBackStack();
    }

    @Override
    public void openTable() {
        showFragment(new OpenTableFragment(), Boolean.TRUE);
    }

    @Override
    public void processOpenTable(@NotNull SaleDTO table) {
        progressBar.show();

        table.setUnitDTO(userService.getUnitDTO());
        saleService.processOpenTable(table, new ResponseListner<SaleDTO>() {
            @Override
            public void success(SaleDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.table_was_successfully_opened), () -> {
                    progressBar.show();
                    saleService.fetchOpenedTables(userService.getUnitDTO().getUuid(), new ResponseListner<SalesDTO>() {
                        @Override
                        public void success(SalesDTO response) {
                            progressBar.dismiss();
                            tables = response.getSalesDTO();
                            popBackStack();
                        }

                        @Override
                        public void error(String message) {
                            progressBar.dismiss();
                            dialogManager.dialog(AlertType.ERROR, getString(R.string.there_was_an_error_loading_tables), null);
                            Log.e("POS_LOADING_TABLES", message);
                        }
                    });
                });
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("POS_OPEN_TABLE_B", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.there_was_an_error_opening_table), null);
                Log.e("POS_OPEN_TABLE", message);
                Log.e("POS_OPEN_TABLE", message);
            }
        });
    }

    @NotNull
    @Override
    public List<SaleDTO> getTables() {
        return tables;
    }

    @Override
    public void selectedTable(@Nullable SaleDTO table) {
        this.table = table;
        showFragment(new TableDetailsFragment(), Boolean.TRUE);
    }

    @NotNull
    @Override
    public SaleDTO getSelectedTable() {
        return table;
    }

    @Override
    public void selectedMenuItem(int iconId) {
        switch (iconId) {
            case R.mipmap.ic_add_order:
                table.cleanItems();
                showFragment(new PosAddOrdersFragment(), Boolean.TRUE);
                break;
            case R.mipmap.ic_payment:
                if (!hasOrders()) {
                    return;
                }

                showFragment(new PosPaymentFragment(), Boolean.TRUE);
                break;
            case R.mipmap.ic_bill:
                if (!hasOrders()) {
                    return;
                }

                loadTable();
                break;
        }
    }

    private boolean hasOrders() {
        if (table.getTotal().compareTo(BigDecimal.ZERO) == BigDecimal.ZERO.intValue()) {
            dialogManager.dialog(AlertType.INFO, getString(R.string.add_orders), null);
            return false;
        }

        return true;
    }

    private void loadTable() {
        progressBar.show();
        saleService.fetchOpenedTableByUuid(table.getUuid(), new ResponseListner<SaleDTO>() {
            @Override
            public void success(SaleDTO response) {
                progressBar.dismiss();
                table = response;
                showFragment(new PosBillFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.there_was_an_error_loading_tables), null);
                Log.e("POS_BILL", message);
            }
        });
    }

    @Override
    public void selectItemType(ItemType itemType) {
        this.itemType = itemType;
        progressBar.show();

        itemService.findItemByUnit(itemType, userService.getUnitDTO(), new ResponseListner<List<ItemDTO>>() {
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
                dialogManager.dialog(AlertType.ERROR, getString(R.string.sale_error_on_add_item), null);
                progressBar.dismiss();
                Log.e("ITEMS", message);
            }
        });
    }

    @Override
    public List<ItemDTO> getItems() {
        return items;
    }

    @Override
    public void selectedItem(ItemDTO itemDTO) {

        KeyboardUtil.hideKeyboard(this, toolbar);
        progressBar.show();

        saleableItemService.findSalebleItemByItemAndUnit(itemDTO, userService.getUnitDTO(), new ResponseListner<List<SaleableItemDTO>>() {
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
    public List<SaleableItemDTO> getSaleableItems() {
        return saleableItems;
    }

    @Override
    public void selectedSaleableItem(SaleableItemDTO saleableItemDTO) {
        saleableItem = saleableItemDTO;
        showFragment(new PosAddOrderItemFragment(), Boolean.TRUE);
    }

    @Override
    public SaleableItemDTO getSaleableItem() {
        return saleableItem;
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public void selectItem() {
        showFragment(new ItemTypeFragment(), Boolean.TRUE);
    }

    @Override
    public void cancel() {
        popBackStack();
        popBackStack();
        popBackStack();
        popBackStack();
    }

    @Override
    public void addSaleItem(@NotNull SaleItemDTO saleItem) {
        table.addSaleItem(saleItem);
        cancel();
    }

    @Override
    public void registAddedItems() {
        if (table.getItems().isEmpty()) {
            dialogManager.dialog(AlertType.INFO, getString(R.string.add_orders), null);
            return;
        }

        progressBar.show();

        saleService.registAddedItems(table, new ResponseListner<SaleDTO>() {
            @Override
            public void success(SaleDTO response) {
                progressBar.dismiss();
                table = response;
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.pos_orders_successfully_added), () -> {
                    resetFragment();
                    loadOpenedTables();
                });
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("POS_ITEMS_B", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.there_was_an_error_adding_orders), null);
                Log.e("POS_ITEMS", message);
            }
        });
    }

    @Override
    public void registPayment(@NotNull SalePaymentDTO payment) {
        KeyboardUtil.hideKeyboard(this, toolbar);

        progressBar.show();

        saleService.salePayment(payment, new ResponseListner<SalePaymentDTO>() {
            @Override
            public void success(SalePaymentDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.payment_success), new AlertListner() {
                    @Override
                    public void perform() {
                        resetFragment();
                        loadOpenedTables();
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.payment_error), null);
                Log.e("POS_PAYMENT", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("POS_PAYMENT_B", errorMessage.getDeveloperMessage());
            }
        });
    }

    @Override
    public void print() {
        salePrinter = new SalePrinterImpl();

        if (!salePrinter.hasDevice()) {
            dialogManager.dialog(AlertType.INFO, getString(R.string.printer_not_connected), null);
            return;
        }

        salePrinter.printReceipt(table, BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo));

        sleep(2);

        popBackStack();
        salePrinter.closeConnection();
    }

    private void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendToWhatsApp() {

        progressBar.show();
        saleService.sendTableBill(table, new ResponseListner<SaleDTO>() {
            @Override
            public void success(SaleDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.whatsapp_bill_successfully_sent), new AlertListner() {
                    @Override
                    public void perform() {
                        popBackStack();
                    }
                });
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), new AlertListner() {
                    @Override
                    public void perform() {
                        popBackStack();
                    }
                });
                Log.e("SEND_BILL_B", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.there_was_an_error_sending_the_bill), new AlertListner() {
                    @Override
                    public void perform() {
                        popBackStack();
                    }
                });
                Log.e("SEND_BILL", message);
            }
        });
    }
}