package mz.co.commandline.grocery.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import kotlin.SuccessOrFailureKt;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.contract.delegate.ContractDelegate;
import mz.co.commandline.grocery.contract.dto.ContractDTO;
import mz.co.commandline.grocery.contract.dto.ContractPaymentDTO;
import mz.co.commandline.grocery.contract.dto.ContractsDTO;
import mz.co.commandline.grocery.contract.fragment.CelebrateContractConfirmationFragment;
import mz.co.commandline.grocery.contract.fragment.CelebrateContractFragment;
import mz.co.commandline.grocery.contract.fragment.ContractPaymentFragment;
import mz.co.commandline.grocery.contract.fragment.ContractsFragment;
import mz.co.commandline.grocery.contract.service.ContractService;
import mz.co.commandline.grocery.customer.delegate.CustomerDelegate;
import mz.co.commandline.grocery.customer.fragment.CustomersFragment;
import mz.co.commandline.grocery.customer.fragment.RegistCustomerFragment;
import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.customer.model.CustomersDTO;
import mz.co.commandline.grocery.customer.service.CustomerService;
import mz.co.commandline.grocery.generics.dto.EnumDTO;
import mz.co.commandline.grocery.generics.dto.EnumsDTO;
import mz.co.commandline.grocery.generics.dto.ErrorMessage;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.main.delegate.MenuDelegate;
import mz.co.commandline.grocery.main.fragment.MenuFragment;
import mz.co.commandline.grocery.menu.Menu;
import mz.co.commandline.grocery.menu.MenuItem;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.DateUtil;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

public class ContractActivity extends BaseAuthActivity implements ContractDelegate, CustomerDelegate {

    private static final int CURRENT_PAGE = 0;

    private static final int MAX_RESULT = 100;

    @Inject
    CustomerService customerService;

    @Inject
    UserService userService;

    @Inject
    ContractService contractService;

    private Menu menu;

    private ContractDTO contractDTO;

    private CustomersDTO customersDTO;

    private List<EnumDTO> contractTypes;

    private MenuItem menuItem;

    private List<ContractDTO> contractDTOs;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_contract);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        Toolbar toolbar = getToolbar();
        toolbar.setTitle(R.string.contracts);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popBackStack();
            }
        });

        menu = new Menu();
        menu.addMenuItem(new MenuItem(R.string.contract, R.mipmap.ic_contracts));
        menu.addMenuItem(new MenuItem(R.string.payment, R.mipmap.ic_payment));

        showFragment(new MenuFragment(), Boolean.FALSE);
    }

    @Override
    public int getActivityFrameLayoutId() {
        return R.id.contract_activity_frame_layout;
    }

    @NotNull
    @Override
    public List<EnumDTO> getContractTypes() {
        return contractTypes;
    }

    @NotNull
    @Override
    public ContractDTO getContract() {
        return contractDTO;
    }

    @Override
    public void celebrateContract() {
        customersDTO = new CustomersDTO();
        progressBar.show();
        loadCustomers();
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
        if (menuItem.getIconId() == R.mipmap.ic_contracts) {
            contractDTO.setCustomerDTO(customerDTO);
            showFragment(new CelebrateContractConfirmationFragment(), Boolean.TRUE);
            return;
        }

        progressBar.show();
        contractService.findPendigPaymentContractsByCustomer(customerDTO.getUuid(), DateUtil.format(new Date(), DateUtil.NORMAL_PATTERN), new ResponseListner<ContractsDTO>() {
            @Override
            public void success(ContractsDTO response) {
                progressBar.dismiss();
                contractDTOs = response.getContracts();
                showFragment(new ContractsFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_contracts), null);
                Log.e("CONTRACTS", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("CONTRACTS_B", errorMessage.getDeveloperMessage());
            }
        });
    }

    @Override
    public int addBtnVisibility() {
        return menuItem.getIconId() == R.mipmap.ic_contracts ? View.VISIBLE : View.GONE;
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return menu.getMenuItems();
    }

    @Override
    public void onClickMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;

        switch (this.menuItem.getIconId()) {
            case R.mipmap.ic_contracts:
                contractDTO = new ContractDTO(userService.getGroceryDTO());
                contractTypes = new ArrayList<>();
                contractTypes.add(new EnumDTO(null, getString(R.string.contract_type)));
                loadContractTypes();
                break;

            case R.mipmap.ic_payment:
                progressBar.show();
                customerService.findCustomersWithContractPendingPaymentByUnit(userService.getGroceryDTO().getUuid(), CURRENT_PAGE, MAX_RESULT, DateUtil.format(new Date(), DateUtil.NORMAL_PATTERN), new ResponseListner<CustomersDTO>() {
                    @Override
                    public void success(CustomersDTO response) {
                        progressBar.dismiss();
                        customersDTO = response;

                        if (customersDTO.getCustomerDTOs().isEmpty()) {
                            dialogManager.dialog(AlertType.INFO, getString(R.string.no_pending_customers_for_payment), null);
                            return;
                        }

                        showFragment(new CustomersFragment(), Boolean.TRUE);
                    }

                    @Override
                    public void error(String message) {
                        progressBar.dismiss();
                        dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_customers), null);
                        Log.e("CUSTOMERS", message);
                    }
                });
                break;
        }
    }

    private void loadContractTypes() {
        progressBar.show();

        contractService.getContractTypes(new ResponseListner<EnumsDTO>() {
            @Override
            public void success(EnumsDTO response) {
                progressBar.dismiss();
                contractTypes.addAll(response.getValues());

                showFragment(new CelebrateContractFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.contracts_type_load_error), null);
                Log.e("CONTRACT_TYPES", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("CONTRACT_TYPES_B", errorMessage.getDeveloperMessage());
            }
        });
    }

    @Override
    public String getFragmentTitle() {
        return getString(R.string.contracts);
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
    public void registContract() {
        progressBar.show();
        contractService.registContract(contractDTO, new ResponseListner<ContractDTO>() {
            @Override
            public void success(ContractDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.contract_registered), new AlertListner() {
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
                dialogManager.dialog(AlertType.ERROR, getString(R.string.contract_registered_error), null);
                Log.e("CUSTOMERS", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("CUSTOMERS_BUS", errorMessage.getDeveloperMessage());
            }
        });
    }

    @NotNull
    @Override
    public List<ContractDTO> getContracts() {
        return contractDTOs;
    }

    @Override
    public void paymentDetails(@NotNull ContractDTO contract) {
        this.contractDTO = contract;
        showFragment(new ContractPaymentFragment(), Boolean.TRUE);
    }

    @Override
    public void registContractPayment(@NotNull ContractPaymentDTO contractPaymentDTO) {
        progressBar.show();
        contractService.registContractPayment(contractPaymentDTO, new ResponseListner<ContractPaymentDTO>() {
            @Override
            public void success(ContractPaymentDTO response) {
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
                Log.e("CONTRACT_PAYMENT", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("CONTRACT_PAYMENT_B", errorMessage.getDeveloperMessage());
            }
        });
    }
}