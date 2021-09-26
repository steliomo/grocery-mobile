package mz.co.commandline.grocery.main.fragment;

import android.content.Intent;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.MenuAdapter;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.main.delegate.MainDelegate;
import mz.co.commandline.grocery.main.delegate.MenuDelegate;
import mz.co.commandline.grocery.menu.Menu;
import mz.co.commandline.grocery.menu.MenuItem;


public class MenuFragment extends BaseFragment implements ClickListner<MenuItem> {

    @BindView(R.id.fragment_menu_recycleview)
    RecyclerView recyclerView;

    private MenuDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_menu;
    }

    @Override
    public void onCreateView() {

        delegate = (MenuDelegate) getActivity();

        Toolbar toolBar = getToolBar();
        toolBar.setTitle(delegate.getFragmentTitle());

        MenuAdapter adapter = new MenuAdapter(getActivity(), delegate.getMenuItems());
        adapter.setItemClickListner(this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListner(MenuItem menuItem) {
        delegate.onClickMenuItem(menuItem);
    }
}
