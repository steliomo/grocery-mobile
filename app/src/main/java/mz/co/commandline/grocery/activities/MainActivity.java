package mz.co.commandline.grocery.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.MenuAdapter;
import mz.co.commandline.grocery.menu.Menu;

public class MainActivity extends BaseAuthActivity {

    @BindView(R.id.main_activity_recycleview)
    RecyclerView recyclerView;

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_main);

        MenuAdapter adapter = new MenuAdapter(this, Menu.getMenuItems());
        recyclerView.setAdapter(adapter);
    }
}
