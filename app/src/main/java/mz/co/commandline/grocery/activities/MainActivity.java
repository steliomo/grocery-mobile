package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.generics.dto.EnumDTO;
import mz.co.commandline.grocery.generics.dto.EnumsDTO;
import mz.co.commandline.grocery.generics.dto.ErrorMessage;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.login.delegate.SignUpDelegate;
import mz.co.commandline.grocery.login.fragment.SignUpUserFragment;
import mz.co.commandline.grocery.main.delegate.MainDelegate;
import mz.co.commandline.grocery.main.fragment.DashboardFragment;
import mz.co.commandline.grocery.main.fragment.MenuFragment;
import mz.co.commandline.grocery.menu.MainMenuBuilder;
import mz.co.commandline.grocery.menu.Menu;
import mz.co.commandline.grocery.menu.MenuItem;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.payment.delegate.PaymentDelegate;
import mz.co.commandline.grocery.payment.dto.PaymentDTO;
import mz.co.commandline.grocery.payment.fragment.MpesaPaymentFragment;
import mz.co.commandline.grocery.payment.fragment.PaymentConfirmationFragment;
import mz.co.commandline.grocery.payment.fragment.PaymentFragment;
import mz.co.commandline.grocery.payment.service.PaymentService;
import mz.co.commandline.grocery.sale.dto.SalesDTO;
import mz.co.commandline.grocery.sale.service.SaleService;
import mz.co.commandline.grocery.user.dto.UnitDetail;
import mz.co.commandline.grocery.user.dto.UserDTO;
import mz.co.commandline.grocery.user.dto.UserRole;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.DateUtil;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

public class MainActivity extends BaseAuthActivity implements MainDelegate, SignUpDelegate, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, PaymentDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_activity_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.main_activity_navigation_drawer)
    NavigationView navigationView;

    @Inject
    UserService userService;

    @Inject
    SaleService saleService;

    @Inject
    PaymentService paymentService;

    private AlertDialogManager dialogManager;

    private ProgressDialog progressBar;

    private SalesDTO sales;

    private List<EnumDTO> paymentTypes;

    private PaymentDTO payment;

    private UnitDetail unitDetail;

    private Menu menu;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_main);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        if (!userService.isLoggedIn()) {
            return;
        }

        dialogManager = new AlertDialogManager(this);
        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        loadDashboardData();

        loadMainMenu();

        toolbar.setNavigationOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);

        hideOperatorUserMenuItems();
        displayUsername();
    }

    private void loadMainMenu() {
        MainMenuBuilder mainMenuBuilder = new MainMenuBuilder();
        menu = mainMenuBuilder.build(userService.getUnitUser().getUserRole());
    }

    private void hideOperatorUserMenuItems() {
        if (UserRole.OPERATOR.equals(userService.getUnitUser().getUserRole())) {
            navigationView.getMenu().findItem(R.id.menu_drawer_add_user).setVisible(Boolean.FALSE);
            navigationView.getMenu().findItem(R.id.menu_drawer_payments).setVisible(Boolean.FALSE);
        }
    }

    private void displayUsername() {
        View headerView = navigationView.getHeaderView(0);
        TextView textView = headerView.findViewById(R.id.header_drawer_textview);
        textView.setText(getString(R.string.hi) + ", " + userService.getFullName());
    }

    @Override
    public void onClick(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull android.view.MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_drawer_logout:
                userService.logout();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.menu_drawer_main_menu:
                showFragment(new MenuFragment(), Boolean.FALSE);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.menu_drawer_dashboard:
                drawerLayout.closeDrawer(GravityCompat.START);
                loadDashboardData();
                break;

            case R.id.menu_drawer_add_user:
                drawerLayout.closeDrawer(GravityCompat.START);
                showFragment(new SignUpUserFragment(), Boolean.FALSE);
                break;

            case R.id.menu_drawer_payments:
                drawerLayout.closeDrawer(GravityCompat.START);
                paymentFragment();
                break;

            case R.id.menu_drawer_pos:
                startActivity(new Intent(this, PosActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }

        return true;
    }

    private void paymentFragment() {
        progressBar.show();
        userService.getUnitDetails(userService.getUnitDTO().getUuid(), new ResponseListner<UnitDetail>() {
            @Override
            public void success(UnitDetail response) {
                progressBar.dismiss();

                payment = new PaymentDTO();
                payment.setUnitUuid(userService.getUnitDTO().getUuid());

                unitDetail = response;
                unitDetail.setManagerName(userService.getFullName());
                showFragment(new PaymentFragment(), Boolean.FALSE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_payment_loading), null);
                Log.e("PAYMENTS", message);
            }
        });
    }

    private void loadDashboardData() {
        progressBar.show();

        saleService.findMonthlyalesPerPeriod(userService.getUnitDTO().getUuid(), DateUtil.getFirstDateOfTheYear(), DateUtil.getLastDateOfTheYear(), new ResponseListner<SalesDTO>() {
            @Override
            public void success(SalesDTO response) {
                progressBar.dismiss();

                sales = response;
                showFragment(new DashboardFragment(), Boolean.FALSE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, "Ocorreu um erro ao processar os dados. Por favor tente novamente", null);
                Log.e("DASHBOARD", message);
            }
        });
    }

    @Override
    public void mpesaMethod() {
        progressBar.show();

        paymentService.getVouchers(new ResponseListner<EnumsDTO>() {
            @Override
            public void success(EnumsDTO response) {
                progressBar.dismiss();

                EnumDTO defaultPaymentType = new EnumDTO(null, getString(R.string.voucher_types));
                paymentTypes = new ArrayList<>();
                paymentTypes.add(defaultPaymentType);
                paymentTypes.addAll(response.getValues());

                showFragment(new MpesaPaymentFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
            }
        });
    }

    @Override
    public void proceed() {
        progressBar.show();
        paymentService.getCalculatedPayment(payment.getVoucher(), new ResponseListner<PaymentDTO>() {
            @Override
            public void success(PaymentDTO response) {
                progressBar.dismiss();

                payment.setDiscountValue(response.getDiscountValue());
                payment.setTotal(response.getTotal());

                showFragment(new PaymentConfirmationFragment(), Boolean.TRUE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();

                Log.e("PAYMENTS", message);
            }
        });
    }

    @Override
    public PaymentDTO getPayment() {
        return this.payment;
    }

    @Override
    public void paymentExecution(PaymentDTO payment) {
        progressBar.show();

        paymentService.makePayment(payment, new ResponseListner<PaymentDTO>() {
            @Override
            public void success(PaymentDTO response) {
                progressBar.dismiss();

                dialogManager.dialog(AlertType.SUCCESS, "O Pagamento foi realizado com sucesso!", new AlertListner() {
                    @Override
                    public void perform() {
                        resetFragment();
                        paymentFragment();
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, "Ocorreu um erro ao realizar o pagamento. por favor tente mais tarde", null);
                Log.e("PAYMENTS", message);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("PAYMENTS_B", errorMessage.getDeveloperMessage());
            }
        });
    }

    @Override
    public List<EnumDTO> getVouchers() {
        return paymentTypes;
    }

    @Override
    public UnitDetail getUnitDetail() {
        return unitDetail;
    }

    @Override
    public SalesDTO getSales() {
        return sales;
    }

    @Override
    public String getFragmentTitle() {
        return getString(R.string.main_menu);
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return menu.getMenuItems();
    }

    @Override
    public void onClickMenuItem(MenuItem menuItem) {
        startActivity(new Intent(this, menuItem.getMenuItemType().getClazz()));
    }

    @Override
    public void signUpNext(UserDTO userDTO) {
        progressBar.show();

        userDTO.getUnitUserDTO().setUnitDTO(userService.getUnitDTO());

        userService.addSaler(userDTO, new ResponseListner<UserDTO>() {
            @Override
            public void success(UserDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.welcome_saler) + " " + response.getEmail(), new AlertListner() {
                    @Override
                    public void perform() {
                        showFragment(new MenuFragment(), Boolean.FALSE);
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_sign_up), new AlertListner() {
                    @Override
                    public void perform() {
                        showFragment(new MenuFragment(), Boolean.FALSE);
                    }
                });
                Log.e("ADD_SALER", message);
            }
        });
    }

    @Override
    public int getActivityFrameLayoutId() {
        return R.id.main_activity_frame_layout;
    }
}
