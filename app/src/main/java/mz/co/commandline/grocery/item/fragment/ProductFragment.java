package mz.co.commandline.grocery.item.fragment;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.item.delegate.ItemDelegate;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.item.adapter.ProductAdapter;
import mz.co.commandline.grocery.item.dto.ItemType;


public class ProductFragment extends BaseFragment implements ClickListner<ItemDTO>, SearchView.OnQueryTextListener {

    @BindView(R.id.fragment_product_recycleview)
    RecyclerView recyclerView;

    private ItemDelegate delegate;

    private ProductAdapter adapter;

    @Override
    public int getResourceId() {
        return R.layout.fragment_product;
    }

    @Override
    public void onCreateView() {

        Toolbar toolBar = getToolBar();
        toolBar.inflateMenu(R.menu.search_menu);

        MenuItem menuItem = toolBar.getMenu().findItem(R.id.search_menu_action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(this);

        delegate = (ItemDelegate) getActivity();
        setTitle(toolBar);

        adapter = new ProductAdapter(getActivity(), delegate.getItemsDTO());
        adapter.setItemClickListner(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void setTitle(Toolbar toolBar) {
        if (ItemType.PRODUCT.equals(delegate.getItemType())) {
            toolBar.setTitle(R.string.products);
            return;
        }
        toolBar.setTitle(R.string.services);
    }

    @Override
    public void onClickListner(ItemDTO itemDTO) {
        delegate.selectedItem(itemDTO);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        adapter.getFilter().filter(query);
        return false;
    }
}
