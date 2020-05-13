package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.main.delegate.MainDelegate;
import mz.co.commandline.grocery.main.fragment.DashboardFragment;
import mz.co.commandline.grocery.main.fragment.MainMenuFragment;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.report.fragment.SaleReportFragment;
import mz.co.commandline.grocery.sale.dto.SalesDTO;
import mz.co.commandline.grocery.sale.service.SaleService;
import mz.co.commandline.grocery.user.dto.UserRole;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.DateUtil;
import mz.co.commandline.grocery.util.FragmentUtil;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertType;

public class MainActivity extends BaseAuthActivity implements MainDelegate, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

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

    private FragmentManager fragmentManager;

    private AlertDialogManager alertDialogManager;

    private ProgressDialog progressBar;

    private SalesDTO sales;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_main);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        if (!userService.isLoggedIn()) {
            return;
        }

        fragmentManager = getSupportFragmentManager();

        alertDialogManager = new AlertDialogManager(this);
        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        loadDashboardData();

        toolbar.setNavigationOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        displayUsername();
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
                FragmentUtil.displayFragment(fragmentManager, R.id.main_activity_frame_layout, new MainMenuFragment(), Boolean.FALSE);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.menu_drawer_dashboard:
                drawerLayout.closeDrawer(GravityCompat.START);
                loadDashboardData();
                break;
        }

        return true;
    }

    private void loadDashboardData() {
        progressBar.show();

        saleService.findMonthlyalesPerPeriod(userService.getGroceryDTO().getUuid(), DateUtil.getFirstDateOfTheYear(), DateUtil.getLastDateOfTheYear(), new ResponseListner<SalesDTO>() {
            @Override
            public void success(SalesDTO response) {
                progressBar.dismiss();

                sales = response;
                FragmentUtil.displayFragment(fragmentManager, R.id.main_activity_frame_layout, new DashboardFragment(), Boolean.FALSE);
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                alertDialogManager.dialog(AlertType.ERROR, "Ocorreu um erro ao processar os dados. Por favor tente novamente", null);
                Log.e("DASHBOARD", message);
            }
        });
    }

    @Override
    public UserRole getUserRole() {
        return userService.getGroceryUser().getUserRole();
    }

    @Override
    public SalesDTO getSales() {
        return sales;
    }
}
