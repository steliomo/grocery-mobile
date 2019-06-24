package mz.co.commandline.grocery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.MenuAdapter;
import mz.co.commandline.grocery.menu.Menu;
import mz.co.commandline.grocery.menu.MenuItem;
import mz.co.commandline.grocery.module.GroceryComponent;

public class MainActivity extends BaseAuthActivity {

    @BindView(R.id.main_activity_recycleview)
    RecyclerView recyclerView;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_main);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        MenuAdapter adapter = new MenuAdapter(this, Menu.getMenuItems());
        setItemClickListner(adapter);
        recyclerView.setAdapter(adapter);
    }

    private void setItemClickListner(MenuAdapter adapter) {
        adapter.setItemClickListner(new ClickListner<MenuItem>() {
            @Override
            public void onClickListner(MenuItem menuItem) {
                startActivity(new Intent(MainActivity.this, menuItem.getMenuItemType().getClazz()));
            }
        });
    }
}
