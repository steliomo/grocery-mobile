package mz.co.commandline.grocery.main.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.MenuAdapter;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.main.delegate.MainDelegate;
import mz.co.commandline.grocery.menu.Menu;
import mz.co.commandline.grocery.menu.MenuItem;


public class MainMenuFragment extends BaseFragment implements ClickListner<MenuItem> {

    @BindView(R.id.fragment_main_menu_recycleview)
    RecyclerView recyclerView;

    @Override
    public int getResourceId() {
        return R.layout.fragment_main_menu;
    }

    @Override
    public void onCreateView() {

        MainDelegate delegate = (MainDelegate) getActivity();

        Menu menu = new Menu(delegate.getUserRole());

        MenuAdapter adapter = new MenuAdapter(getActivity(), menu.getMenuItems());
        adapter.setItemClickListner(this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public String getTitle() {
        return getString(R.string.main_menu);
    }

    @Override
    public void onClickListner(MenuItem menuItem) {
        startActivity(new Intent(getActivity(), menuItem.getMenuItemType().getClazz()));
    }
}
