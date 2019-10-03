package mz.co.commandline.grocery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.MenuAdapter;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.menu.Menu;
import mz.co.commandline.grocery.menu.MenuItem;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.user.service.UserService;

public class MainActivity extends BaseAuthActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_activity_recycleview)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_activity_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.main_activity_navigation_drawer)
    NavigationView navigationView;

    @Inject
    UserService userService;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_main);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        if (!userService.isLoggedIn()) {
            return;
        }

        Menu menu = new Menu(userService.getGroceryUser().getUserRole());

        MenuAdapter adapter = new MenuAdapter(this, menu.getMenuItems());
        setItemClickListner(adapter);
        recyclerView.setAdapter(adapter);

        toolbar.setNavigationOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        displayUsername();
    }

    private void displayUsername() {
        View headerView = navigationView.getHeaderView(0);
        TextView textView = headerView.findViewById(R.id.header_drawer_textview);
        textView.setText(getString(R.string.hi) + ", " + userService.getFullName());
    }

    private void setItemClickListner(MenuAdapter adapter) {
        adapter.setItemClickListner(new ClickListner<MenuItem>() {
            @Override
            public void onClickListner(MenuItem menuItem) {
                startActivity(new Intent(MainActivity.this, menuItem.getMenuItemType().getClazz()));
            }
        });
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
            case R.id.mennu_drawer_logout:
                userService.logout();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
                break;
        }

        return true;
    }
}
