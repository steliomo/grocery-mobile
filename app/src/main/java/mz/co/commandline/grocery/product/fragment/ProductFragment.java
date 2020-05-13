package mz.co.commandline.grocery.product.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.product.adapter.ProductAdapter;
import mz.co.commandline.grocery.product.delegate.ProductDelegate;
import mz.co.commandline.grocery.product.dto.ProductDTO;


public class ProductFragment extends BaseFragment implements ClickListner<ProductDTO>, SearchView.OnQueryTextListener {

    @BindView(R.id.fragment_product_recycleview)
    RecyclerView recyclerView;

    private ProductDelegate delegate;

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

        delegate = (ProductDelegate) getActivity();
        adapter = new ProductAdapter(getActivity(), delegate.getProductsDTO());
        adapter.setItemClickListner(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListner(ProductDTO productDTO) {
        delegate.selectedProduct(productDTO);
    }

    @Override
    public String getTitle() {
        return getString(R.string.products);
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
