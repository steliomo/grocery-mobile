package mz.co.commandline.grocery.main.delegate;

import java.util.List;

import mz.co.commandline.grocery.menu.MenuItem;

public interface MenuDelegate {

    List<MenuItem> getMenuItems();

    void onClickMenuItem(MenuItem menuItem);

    String getFragmentTitle();
}
