package mz.co.commandline.grocery.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;
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
import mz.co.commandline.grocery.files.FileService;
import mz.co.commandline.grocery.generics.dto.ErrorMessage;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.item.delegate.ItemDelegate;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.item.dto.ItemType;
import mz.co.commandline.grocery.item.fragment.ProductFragment;
import mz.co.commandline.grocery.item.service.ItemService;
import mz.co.commandline.grocery.main.fragment.MenuFragment;
import mz.co.commandline.grocery.menu.Menu;
import mz.co.commandline.grocery.menu.MenuItem;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.rent.delegate.RentDelegate;
import mz.co.commandline.grocery.rent.dto.GuideDTO;
import mz.co.commandline.grocery.rent.dto.GuideItemDTO;
import mz.co.commandline.grocery.rent.dto.RentDTO;
import mz.co.commandline.grocery.rent.dto.RentItemDTO;
import mz.co.commandline.grocery.rent.dto.RentPaymentDTO;
import mz.co.commandline.grocery.rent.dto.RentReport;
import mz.co.commandline.grocery.rent.dto.RentType;
import mz.co.commandline.grocery.rent.dto.RentsDTO;
import mz.co.commandline.grocery.rent.dto.ReturnItemDTO;
import mz.co.commandline.grocery.rent.fragment.AddRentItemFragment;
import mz.co.commandline.grocery.rent.fragment.DevolutionsFragment;
import mz.co.commandline.grocery.rent.fragment.PendingRentsFragment;
import mz.co.commandline.grocery.rent.fragment.QuotationFragment;
import mz.co.commandline.grocery.rent.fragment.RegistRentFragment;
import mz.co.commandline.grocery.rent.fragment.RentItemsFragment;
import mz.co.commandline.grocery.rent.fragment.RentPaymentFragment;
import mz.co.commandline.grocery.rent.fragment.ReturnItemFragment;
import mz.co.commandline.grocery.rent.fragment.TransportFragment;
import mz.co.commandline.grocery.rent.fragment.TransportItemFragment;
import mz.co.commandline.grocery.rent.service.RentService;
import mz.co.commandline.grocery.sale.fragment.ItemTypeFragment;
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO;
import mz.co.commandline.grocery.saleable.fragment.StockFragment;
import mz.co.commandline.grocery.saleable.service.SaleableItemService;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.FileUtil;
import mz.co.commandline.grocery.util.KeyboardUtil;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;
import okhttp3.ResponseBody;

public class RentActivity extends BaseAuthActivity implements View.OnClickListener, RentDelegate, ItemDelegate, CustomerDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ItemService itemService;

    @Inject
    SaleableItemService saleableItemService;

    @Inject
    UserService userService;

    @Inject
    CustomerService customerService;

    @Inject
    RentService rentService;

    @Inject
    FileService fileService;

    private Menu menu;

    private List<ItemDTO> items;

    private ItemType itemType;

    private List<SaleableItemDTO> saleableItems;

    private SaleableItemDTO saleableItemDTO;

    private RentDTO rent;

    private CustomersDTO customersDTO;

    private List<RentDTO> rentsDTO;

    private RentType rentType;

    private RentItemDTO rentItemDTO;

    //Change
    private final int CURRENT_PAGE = 0;

    private final int MAX_RESULT = 100;

    private CustomerDTO customerDTO;

    private List<ReturnItemDTO> returnItemsDTO = new ArrayList<>();

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_rent);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        toolbar.setTitle(R.string.rents);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        menu = new Menu();
        menu.addMenuItem(new MenuItem(R.string.rent, R.mipmap.ic_rents));
        menu.addMenuItem(new MenuItem(R.string.payment, R.mipmap.ic_payment));
        menu.addMenuItem(new MenuItem(R.string.transport_guide, R.mipmap.ic_transport));
        menu.addMenuItem(new MenuItem(R.string.devolution_guide, R.mipmap.ic_return));
        menu.addMenuItem(new MenuItem(R.string.quotation, R.mipmap.ic_quotation));

        showFragment(new MenuFragment(), Boolean.FALSE);
    }

    private void resetPage() {
        itemType = null;
        saleableItemDTO = null;
        rent = null;
        customersDTO = null;
        rentType = null;
        rentItemDTO = null;

        items = new ArrayList<>();
        rentsDTO = new ArrayList<>();
        saleableItems = new ArrayList<>();
        returnItemsDTO = new ArrayList<>();
    }

    @Override
    public int getActivityFrameLayoutId() {
        return R.id.rent_activity_frame_layout;
    }

    @Override
    public void onClick(View view) {
        popBackStack();
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return menu.getMenuItems();
    }

    @Override
    public void onClickMenuItem(MenuItem menuItem) {
        switch (menuItem.getIconId()) {
            case R.mipmap.ic_rents:
                resetPage();
                rentType = RentType.RENT;
                showFragment(new RentItemsFragment(), Boolean.TRUE);
                rent = new RentDTO(userService.getGroceryDTO());
                break;

            case R.mipmap.ic_payment:
                resetPage();
                rentType = RentType.PAYMENT;
                loadCustomersWithPendingPayments();
                break;

            case R.mipmap.ic_transport:
                resetPage();
                rentType = RentType.TRANSPORT;
                loadCustomersWithPendingOrInCompleteRentItemsToLoadByUnit();
                break;

            case R.mipmap.ic_return:
                resetPage();
                rentType = RentType.RETURN;
                loadCustomersWithPendingOrIncompleteRentItemsToReturnByUnit();
                break;

            case R.mipmap.ic_quotation:
                resetPage();
                rentType = RentType.QUOTATION;
                showFragment(new QuotationFragment(), Boolean.TRUE);
                rent = new RentDTO(userService.getGroceryDTO());
                break;
        }
    }

    private void loadCustomersWithPendingOrInCompleteRentItemsToLoadByUnit() {
        progressBar.show();
        customerService.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(userService.getGroceryDTO().getUuid(), new ResponseListner<CustomersDTO>() {
            @Override
            public void success(CustomersDTO response) {
                progressBar.dismiss();
                customersDTO = response;

                if (customersDTO.getCustomerDTOs().isEmpty()) {
                    dialogManager.dialog(AlertType.INFO, getString(R.string.no_pending_or_incomplete_customers_with_rent_items_to_load), null);
                    return;
                }

                showFragment(new CustomersFragment(), Boolean.TRUE);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("B_TRANSPORT_GUIDE", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_customers), null);
                Log.e("TRANSPORT_GUIDE", message);
            }
        });
    }

    private void loadCustomersWithPendingOrIncompleteRentItemsToReturnByUnit() {
        progressBar.show();
        customerService.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(userService.getGroceryDTO().getUuid(), CURRENT_PAGE, MAX_RESULT, new ResponseListner<CustomersDTO>() {
            @Override
            public void success(CustomersDTO response) {
                progressBar.dismiss();
                customersDTO = response;

                if (customersDTO.getCustomerDTOs().isEmpty()) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.unit_without_pending_or_incomplete_devolutions), null);
                    return;
                }

                showFragment(new CustomersFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_customers), null);
                Log.e("PENDING_DEVOLUTIONS", message);
            }
        });
    }

    private void loadCustomersWithPendingPayments() {
        progressBar.show();
        customerService.findCustomersWithPendingPaymentsByUnit(userService.getGroceryDTO().getUuid(), CURRENT_PAGE, MAX_RESULT, new ResponseListner<CustomersDTO>() {
            @Override
            public void success(CustomersDTO response) {
                progressBar.dismiss();
                customersDTO = response;

                if (customersDTO.getCustomerDTOs().isEmpty()) {
                    dialogManager.dialog(AlertType.ERROR, getString(R.string.unit_without_pending_payments), null);
                    return;
                }

                showFragment(new CustomersFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_customers), null);
                Log.e("PENDING_PAYMENTS", message);
            }
        });
    }

    @Override
    public String getFragmentTitle() {
        return getString(R.string.rents);
    }

    @Override
    public void selectItem() {
        showFragment(new ItemTypeFragment(), Boolean.TRUE);
    }

    @Override
    public void addRentItem(RentItemDTO rentItemDTO) {
        resetFragment();
        rent.addRentItem(rentItemDTO);

        if (RentType.RENT.equals(rentType)) {
            showFragment(new RentItemsFragment(), Boolean.TRUE);
            return;
        }

        rent.setUnitDTO(rentItemDTO.getUnit());
        showFragment(new QuotationFragment(), Boolean.TRUE);
    }

    @Override
    public RentDTO getRent() {
        return rent;
    }

    @Override
    public void nextStep() {
        if (rent.getRentItemsDTO().isEmpty()) {
            dialogManager.dialog(AlertType.ERROR, getString(R.string.select_items), null);
            return;
        }

        loadCustomers();
    }

    @Override
    public void registRent() {
        progressBar.show();
        rentService.registService(rent, new ResponseListner<RentDTO>() {
            @Override
            public void success(RentDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.rent_registed_with_success), new AlertListner() {
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
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_registing_rent), null);
                Log.e("REGIST_RENT", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("REGIST_RENT_B", errorMessage.getDeveloperMessage());
            }
        });
    }

    @Override
    public List<RentDTO> getRentsDTO() {
        return rentsDTO;
    }

    @Override
    public void selectedPendingRent(RentDTO rentDTO) {
        this.rent = rentDTO;

        switch (rentType) {
            case PAYMENT:
                showFragment(new RentPaymentFragment(), Boolean.TRUE);
                break;

            case TRANSPORT:
                showFragment(new TransportFragment(), Boolean.TRUE);
                break;

            case RETURN:
                showFragment(new DevolutionsFragment(), Boolean.TRUE);
                break;
        }
    }

    @Override
    public void makePayment(RentPaymentDTO rentPaymentDTO) {
        progressBar.show();
        rentService.makePayment(rentPaymentDTO, new ResponseListner<RentPaymentDTO>() {
            @Override
            public void success(RentPaymentDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.payment_success), new AlertListner() {
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
                dialogManager.dialog(AlertType.ERROR, getString(R.string.payment_error), null);
                Log.e("RENT_PAYMENT", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("RENT_PAYMENT_B", errorMessage.getDeveloperMessage());
            }
        });
    }

    @Override
    public void selectedRentItem(RentItemDTO rentItemDTO) {
        this.rentItemDTO = rentItemDTO;

        switch (rentType) {
            case RETURN:
                showFragment(new ReturnItemFragment(), Boolean.TRUE);
                break;

            case TRANSPORT:
                showFragment(new TransportItemFragment(), Boolean.TRUE);
                break;
        }
    }

    @Override
    public RentItemDTO getRentItemDTO() {
        return rentItemDTO;
    }

    @Override
    public void issueTransportGuide() {
        rent.setUnitDTO(userService.getGroceryDTO());
        rent.setCustomerDTO(customerDTO);
        GuideDTO guideDTO = new GuideDTO(rent, rentType.toString());

        for (RentItemDTO rentItemDTO : rent.getRentItemsDTO()) {
            if (rentItemDTO.isSelected()) {
                guideDTO.addGuideItemDTO(new GuideItemDTO(rentItemDTO, rentItemDTO.getQuantity()));
            }
        }

        if (guideDTO.getGuideItemsDTO().isEmpty()) {
            dialogManager.dialog(AlertType.INFO, getString(R.string.no_item_selected), null);
            return;
        }

        progressBar.show();
        rentService.issueTransportGuide(guideDTO, new ResponseListner<GuideDTO>() {
            @Override
            public void success(GuideDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.transport_guide_successfuly_issued), () -> {
                    resetFragment();
                    showFragment(new MenuFragment(), Boolean.FALSE);
                    loadFile(response.getFileName());
                });
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("B_TRANSPORT_GUIDE", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_issuing_guide), null);
                Log.e("TRANSPORT_GUIDE", message);
            }
        });
    }

    private void loadFile(String fileName) {
        fileService.loadPdfFile(fileName, new ResponseListner<ResponseBody>() {
            @Override
            public void success(ResponseBody fileResponse) {
                displayFile(fileResponse, fileName);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("LOAD_FILE", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_file), null);
                Log.e("LOAD_FILE", message);
            }
        });
    }

    @Override
    public void issueReturnGuide() {
        rent.setUnitDTO(userService.getGroceryDTO());
        rent.setCustomerDTO(customerDTO);
        GuideDTO guideDTO = new GuideDTO(rent, rentType.toString());

        for (RentItemDTO rentItemDTO : rent.getRentItemsDTO()) {
            if (rentItemDTO.isSelected()) {
                guideDTO.addGuideItemDTO(new GuideItemDTO(rentItemDTO, rentItemDTO.getQuantity()));
            }
        }

        if (guideDTO.getGuideItemsDTO().isEmpty()) {
            dialogManager.dialog(AlertType.INFO, getString(R.string.no_item_selected), null);
            return;
        }

        progressBar.show();
        rentService.issueReturnGuide(guideDTO, new ResponseListner<GuideDTO>() {
            @Override
            public void success(GuideDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.return_guide_was_successfully_issued), () -> {
                    resetFragment();
                    showFragment(new MenuFragment(), Boolean.FALSE);
                    loadFile(response.getFileName());
                });
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("B_RETURN_ITEMS", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_issuing_return_guide), null);
                Log.e("RETURN_ITEMS", message);
            }
        });
    }

    private void loadCustomers() {
        progressBar.show();
        customerService.findCustomersByUnit(userService.getGroceryDTO().getUuid(), CURRENT_PAGE, MAX_RESULT, new ResponseListner<CustomersDTO>() {
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

    @Override
    public void selectItemType(ItemType itemType) {
        this.itemType = itemType;
        progressBar.show();

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
    public List<SaleableItemDTO> getSaleableItems() {
        return saleableItems;
    }

    @Override
    public void selectedSaleableItem(SaleableItemDTO saleableItemDTO) {
        this.saleableItemDTO = saleableItemDTO;
        showFragment(new AddRentItemFragment(), Boolean.TRUE);
    }

    @Override
    public SaleableItemDTO getSaleableItem() {
        return saleableItemDTO;
    }

    @Override
    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public void addCustomer() {
        showFragment(new RegistCustomerFragment(), Boolean.TRUE);
    }

    @Override
    public void registCustomer(CustomerDTO customerDTO) {
        customerDTO.setUnit(userService.getGroceryDTO());
        progressBar.show();

        customerService.registCustomer(customerDTO, new ResponseListner<CustomerDTO>() {
            @Override
            public void success(CustomerDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.user_was_successfully_registed), new AlertListner() {
                    @Override
                    public void perform() {
                        loadCustomers();
                        popBackStack();
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
        this.customerDTO = customerDTO;
        switch (rentType) {
            case RENT:
                rent.setCustomerDTO(customerDTO);
                showFragment(new RegistRentFragment(), Boolean.TRUE);
                break;

            case PAYMENT:
                loadPendingPaymentsRentsByCustomer(customerDTO);
                break;

            case TRANSPORT:
                loadRentsWithPendingOrIncompleteRentItemToTransportByCustomer(customerDTO);
                break;

            case RETURN:
                loadRentsWithPendingOrIncompleteRentItemToReturnByCustomer(customerDTO);
                break;

            case QUOTATION:
                rent.setCustomerDTO(customerDTO);
                popBackStack();
                dialogManager.dialog(AlertType.INFO, getString(R.string.confirm_the_request), () -> processQuotation());
                break;
        }
    }

    private void loadRentsWithPendingOrIncompleteRentItemToReturnByCustomer(CustomerDTO customerDTO) {
        progressBar.show();
        rentService.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(customerDTO.getUuid(), new ResponseListner<RentsDTO>() {
            @Override
            public void success(RentsDTO response) {
                progressBar.dismiss();
                rentsDTO = response.getRentsDTO();
                showFragment(new PendingRentsFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_pending_rents), null);
                Log.e("PENDING_RETURN", message);
            }
        });
    }

    private void processQuotation() {
        progressBar.show();
        rentService.issueQuotation(rent, new ResponseListner<RentReport>() {
            @Override
            public void success(final RentReport response) {
                fileService.loadPdfFile(response.getName(), new ResponseListner<ResponseBody>() {
                    @Override
                    public void success(final ResponseBody body) {
                        progressBar.dismiss();
                        dialogManager.dialog(AlertType.SUCCESS, getString(R.string.quotation_successfuly_processed), () -> {
                            popBackStack();
                            displayFile(body, response.getName());
                        });
                    }

                    @Override
                    public void error(String message) {
                        progressBar.dismiss();
                        dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_quotation), null);
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_processing_quotation), null);
                Log.e("PROCESSING_QUOTATION", message);
            }
        });
    }

    private void displayFile(ResponseBody body, String fileName) {
        FileUtil fileUtil = new FileUtil(RentActivity.this);
        File file = fileUtil.save(body.byteStream(), fileName);

        Intent target = new Intent(Intent.ACTION_VIEW);
        Uri uriForFile = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
        target.setDataAndType(uriForFile, "application/pdf");
        target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent chooser = Intent.createChooser(target, getString(R.string.open_with));

        startActivity(chooser);
    }

    @Override
    public int addBtnVisibility() {
        if (RentType.RENT.equals(rentType) || RentType.QUOTATION.equals(rentType)) {
            return View.VISIBLE;
        }
        return View.GONE;
    }

    private void loadRentsWithPendingOrIncompleteRentItemToTransportByCustomer(CustomerDTO customerDTO) {
        progressBar.show();

        rentService.fetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer(customerDTO.getUuid(), new ResponseListner<RentsDTO>() {
            @Override
            public void success(RentsDTO response) {
                progressBar.dismiss();
                rentsDTO = response.getRentsDTO();

                showFragment(new PendingRentsFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_pending_rents), null);
                Log.e("PENDING_TRANSPORT", message);
            }
        });
    }

    private void loadPendingPaymentsRentsByCustomer(final CustomerDTO customerDTO) {
        progressBar.show();

        rentService.findPendingPaymentsRentsByCustomer(customerDTO.getUuid(), new ResponseListner<RentsDTO>() {
            @Override
            public void success(RentsDTO response) {
                progressBar.dismiss();

                rentsDTO = response.getRentsDTO();

                for (RentDTO rentDTO : rentsDTO) {
                    rentDTO.setCustomerDTO(customerDTO);
                    rentDTO.setUnitDTO(userService.getGroceryDTO());
                }

                showFragment(new PendingRentsFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_pending_rents), null);
                Log.e("PENDING_PAYMENTS", message);
            }
        });
    }
}