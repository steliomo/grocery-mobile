package mz.co.commandline.grocery.activities;

import android.Manifest;
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
import mz.co.commandline.grocery.customer.adapter.CustomerAdapter;
import mz.co.commandline.grocery.customer.delegate.CustomerDelegate;
import mz.co.commandline.grocery.customer.fragment.CustomersFragment;
import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.customer.model.CustomersDTO;
import mz.co.commandline.grocery.customer.service.CustomerService;
import mz.co.commandline.grocery.generics.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.generics.dto.ErrorMessage;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.item.fragment.ProductFragment;
import mz.co.commandline.grocery.item.service.ItemService;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.pos.delegate.PosDelegate;
import mz.co.commandline.grocery.pos.dto.DebtDTO;
import mz.co.commandline.grocery.pos.dto.DebtItemDTO;
import mz.co.commandline.grocery.pos.fragment.OpenTableCustomerFragment;
import mz.co.commandline.grocery.pos.fragment.OpenTableDetailsFragment;
import mz.co.commandline.grocery.pos.fragment.PosAddOrderItemFragment;
import mz.co.commandline.grocery.pos.fragment.PosAddOrdersFragment;
import mz.co.commandline.grocery.pos.fragment.PosBillFragment;
import mz.co.commandline.grocery.pos.fragment.PosCancelFragment;
import mz.co.commandline.grocery.pos.fragment.PosDebtMenuFragment;
import mz.co.commandline.grocery.pos.fragment.PosFragment;
import mz.co.commandline.grocery.pos.fragment.PosMenuFragment;
import mz.co.commandline.grocery.pos.fragment.PosPayDebtFragment;
import mz.co.commandline.grocery.pos.fragment.PosPaymentFragment;
import mz.co.commandline.grocery.pos.fragment.PosPrintDebtFragment;
import mz.co.commandline.grocery.pos.fragment.SelectTableFragment;
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
import mz.co.commandline.grocery.util.alert.OptionDialog;

public class PosActivity extends BaseAuthActivity implements View.OnClickListener, PosDelegate, CustomerDelegate {

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

    @Inject
    CustomerService customerService;

    private OptionDialog optionDialog;

    private List<SaleDTO> tables;

    private SaleDTO table;

    private ItemType itemType;

    private List<ItemDTO> items;

    private List<SaleableItemDTO> saleableItems;

    private SaleableItemDTO saleableItem;

    private SalePrinter salePrinter;

    private CustomersDTO customersDTO;

    private int currentPage = 0;

    private int maxResult = 10;

    private Boolean manageDebt;

    private Boolean toPayDebt;

    private DebtDTO debt;

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

        showFragment(new PosMenuFragment(), Boolean.FALSE);
    }

    private void loadOpenedTables() {
        progressBar.show();
        saleService.fetchOpenedTables(userService.getUnitDTO().getUuid(), new ResponseListner<SalesDTO>() {
            @Override
            public void success(SalesDTO response) {
                progressBar.dismiss();
                tables = response.getSalesDTO();

                if(tables.isEmpty()){
                    dialogManager.dialog(AlertType.INFO, getString(R.string.pos_unit_empty), null);
                }

                showFragment(new PosFragment(), Boolean.TRUE);
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
        currentPage = 0;
        table = new SaleDTO();
        table.setUnitDTO(userService.getUnitDTO());
        loadCustomers();
    }

    private void loadCustomers() {
        progressBar.show();
        customerService.findCustomersByUnit(userService.getUnitDTO().getUuid(), currentPage, maxResult, new ResponseListner<CustomersDTO>() {
            @Override
            public void success(CustomersDTO response) {
                progressBar.dismiss();
                customersDTO = response;

                if (customersDTO.getCustomerDTOs().isEmpty()) {
                    dialogManager.dialog(AlertType.INFO, getString(R.string.no_customers_found), null);
                    return;
                }
                showFragment(new CustomersFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_customers), null);
                Log.e("POS_LOAD_CUSTOMERS", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.INFO, errorMessage.getMessage(), null);
                Log.e("POS_LOAD_CUSTOMERS_B", errorMessage.getDeveloperMessage());
            }
        });
    }

    @Override
    public void processOpenTable(@NotNull SaleDTO table) {
        progressBar.show();

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
                            resetFragment();
                            showFragment(new PosMenuFragment(), Boolean.FALSE);
                            showFragment(new PosFragment(), Boolean.TRUE);
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
    public SaleDTO getTable() {
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

                loadTable(new PosBillFragment());
                break;

            case R.mipmap.ic_cancel:
                loadTable(new PosCancelFragment());
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

    private void loadTable(BaseFragment fragment) {
        progressBar.show();
        saleService.fetchOpenedTableByUuid(table.getUuid(), new ResponseListner<SaleDTO>() {
            @Override
            public void success(SaleDTO response) {
                progressBar.dismiss();
                table = response;
                showFragment(fragment, Boolean.TRUE);
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
                    showFragment(new PosMenuFragment(), Boolean.FALSE);
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
        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_CONNECT},1);

        salePrinter = new SalePrinterImpl();

        if (!salePrinter.hasDevice()) {
            dialogManager.dialog(AlertType.INFO, getString(R.string.printer_not_connected), null);
            return;
        }

        if(manageDebt){
            progressBar.show();
            saleService.findDebtItemsbByCustomer(debt.getCustomer().getUuid(), new ResponseListner<List<DebtItemDTO>>() {

                @Override
                public void success(List<DebtItemDTO> response) {
                    progressBar.dismiss();

                    debt.getDebtItems().addAll(response);
                    salePrinter.printDept(debt, BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo));

                    sleep(2);

                    resetFragment();
                    showFragment(new PosMenuFragment(), Boolean.FALSE);
                    salePrinter.closeConnection();
                }

                @Override
                public void error(String message) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_dept_items), null);
                }
            });

            return;
        }

        salePrinter.printReceipt(table, BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo));

        sleep(2);

        popBackStack();
        salePrinter.closeConnection();
    }

    @Override
    public void sendToWhatsApp() {
        if(manageDebt){
            progressBar.show();
            saleService.sendCustomerDebt(debt, new ResponseListner<Void>() {
                @Override
                public void success(Void response) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.SUCCESS, getString(R.string.whatsapp_bill_successfully_sent), () -> {
                        resetFragment();
                        showFragment(new PosMenuFragment(), Boolean.FALSE);
                    });
                }

                @Override
                public void businessError(ErrorMessage errorMessage) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                    Log.e("SEND_DEPT_B", errorMessage.getDeveloperMessage());
                }

                @Override
                public void error(String message) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.there_was_an_error_sending_the_bill), null);
                    Log.e("SEND_DEPT", message);
                }
            });

            return;
        }

        progressBar.show();
        saleService.sendTableBill(table, new ResponseListner<SaleDTO>() {
            @Override
            public void success(SaleDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.whatsapp_bill_successfully_sent), () -> popBackStack());
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), () -> popBackStack());
                Log.e("SEND_BILL_B", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.there_was_an_error_sending_the_bill), () -> popBackStack());
                Log.e("SEND_BILL", message);
            }
        });
    }

    @Override
    public void selectedTableNumber(int tableNumber) {
        table.setTableNumber(tableNumber);
        showFragment(new OpenTableDetailsFragment(), Boolean.TRUE);
    }

    @Override
    public void addCustomer() {
        showFragment(new OpenTableCustomerFragment(), Boolean.TRUE);
    }

    @Override
    public void registCustomer(CustomerDTO customerDTO) {
        currentPage = 0;
        progressBar.show();

        customerDTO.setUnit(userService.getUnitDTO());
        customerDTO.setAddress("NA");

        customerService.registCustomer(customerDTO, new ResponseListner<mz.co.commandline.grocery.customer.model.CustomerDTO>() {
            @Override
            public void success(mz.co.commandline.grocery.customer.model.CustomerDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.customer_was_successfully_registed), () -> {
                    popBackStack();
                    popBackStack();
                    loadCustomers();
                });
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("B_POS_REGIST_CUSTOMER", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.there_was_an_error_registing_customer), null);
                Log.e("POS_REGIST_CUSTOMER", message);
            }
        });
    }

    @Override
    public CustomersDTO getCustomersDTO() {
        return customersDTO;
    }

    @Override
    public void selectedCustomer(CustomerDTO customerDTO) {
        if(manageDebt){

            progressBar.show();
            saleService.findDebtByCustomer(customerDTO.getUuid(), new ResponseListner<DebtDTO>() {
                @Override
                public void success(DebtDTO response) {
                    progressBar.dismiss();
                    debt = response;

                    customerDTO.setUnit(userService.getUnitDTO());
                    debt.setCustomer(customerDTO);

                    if(toPayDebt){
                        showFragment(new PosPayDebtFragment(), Boolean.TRUE);
                        return;
                    }

                    showFragment(new PosPrintDebtFragment(), Boolean.TRUE);
                }

                @Override
                public void error(String message) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.pos_find_debt), null);
                    Log.e("POS_LOAD_DEPT", message);
                }

                @Override
                public void businessError(ErrorMessage errorMessage) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(),null);
                    Log.e("POS_LOAD_DEPT_B", errorMessage.getDeveloperMessage());
                }
            });
            return;
        }

        table.setCustomerDTO(customerDTO);
        showFragment(new SelectTableFragment(), Boolean.TRUE);
    }

    @Override
    public int addBtnVisibility() {
        if(manageDebt){
            return View.GONE;
        }
        return View.VISIBLE;
    }

    @Override
    public void updateData(CustomerAdapter adapter) {
        currentPage++;
        progressBar.show();

        if(manageDebt){
            customerService.findCustomersInDeptByUnit(userService.getUnitDTO().getUuid(), currentPage, maxResult, new ResponseListner<CustomersDTO>() {
                @Override
                public void success(CustomersDTO response) {
                    progressBar.dismiss();
                    customersDTO.getCustomerDTOs().addAll(response.getCustomerDTOs());
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void error(String message) {
                    progressBar.dismiss();
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_customers), null);
                    Log.e("DEPT_LOAD_CUSTOMERS", message);
                }
            });

            return;
        }

        customerService.findCustomersByUnit(userService.getUnitDTO().getUuid(), currentPage, maxResult, new ResponseListner<CustomersDTO>() {
            @Override
            public void success(CustomersDTO response) {
                progressBar.dismiss();
                customersDTO.getCustomerDTOs().addAll(response.getCustomerDTOs());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_customers), null);
                Log.e("POS_LOAD_CUSTOMERS", message);
            }
        });
    }

    @Override
    public int numberOfTables() {
        return userService.getUnitDTO().getNumberOfTables();
    }

    @Override
    public void cancelTable() {
        progressBar.show();

        saleService.cancelTable(table, new ResponseListner<SaleDTO>() {
            @Override
            public void success(SaleDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.pos_table_was_successfully_canceled), () -> {
                    resetFragment();
                    showFragment(new PosMenuFragment(), Boolean.FALSE);
                    loadOpenedTables();
                });
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("B_POS_CANCEL_TABLE", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.pos_there_was_an_error_caneling_tabe), null);
                Log.e("POS_CANCEL_TABLE", message);
            }
        });
    }

    @Override
    public void registCreditSale(@NotNull String tableUuid) {
        KeyboardUtil.hideKeyboard(this, toolbar);
        progressBar.show();

        saleService.registCreditSale(tableUuid, new ResponseListner<SaleDTO>() {
            @Override
            public void success(SaleDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.credit_payment_success), () -> {
                    resetFragment();
                    showFragment(new PosMenuFragment(), Boolean.FALSE);
                    loadOpenedTables();
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.payment_error), null);
                Log.e("POS_CREDIT_PAYMENT", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("POS_CREDIT_PAYMENT_B", errorMessage.getDeveloperMessage());
            }
        });
    }

    @Override
    public void selectedPosMenuItem(int iconId) {
        manageDebt = Boolean.FALSE;

        if(R.mipmap.ic_table == iconId){
            loadOpenedTables();
            return;
        }

        manageDebt = Boolean.TRUE;
        showFragment(new PosDebtMenuFragment(), Boolean.TRUE);
    }

    @Override
    public void selectedPosDeptMenu(int iconId) {
        toPayDebt = Boolean.FALSE;
        currentPage = 0;

        if(R.mipmap.ic_payment == iconId){
            toPayDebt = Boolean.TRUE;

            loadCustomersInDept();
            return;
        }

        loadCustomersInDept();
    }

    private void loadCustomersInDept() {
        progressBar.show();
        customerService.findCustomersInDeptByUnit(userService.getUnitDTO().getUuid(), currentPage, maxResult, new ResponseListner<CustomersDTO>() {
            @Override
            public void success(CustomersDTO response) {
                progressBar.dismiss();

                customersDTO = response;

                if (customersDTO.getCustomerDTOs().isEmpty()) {
                    dialogManager.dialog(AlertType.INFO, getString(R.string.no_customers_found), null);
                    return;
                }

                showFragment(new CustomersFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_customers), null);
                Log.e("DEPT_LOAD_CUSTOMERS", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("DEPT_LOAD_CUSTOMERS_B", errorMessage.getDeveloperMessage());
            }
        });
    }

    @NotNull
    @Override
    public DebtDTO getDebt() {
        return debt;
    }

    @Override
    public void payDept(@NotNull DebtDTO dept) {
        progressBar.show();

        saleService.payDebt(dept, new ResponseListner<DebtDTO>() {
            @Override
            public void success(DebtDTO response) {
                progressBar.dismiss();

                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.pos_pay_dept_sucess), () ->{
                    resetFragment();
                    showFragment(new PosMenuFragment(), Boolean.FALSE);
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.pos_pay_dept_error), null);
                Log.e("PAY_DEPT_ERROR", message);

            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("PAY_DEPT_ERROR_B", errorMessage.getDeveloperMessage());
            }
        });
    }
}