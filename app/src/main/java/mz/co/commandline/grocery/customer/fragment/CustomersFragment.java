package mz.co.commandline.grocery.customer.fragment;

import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.customer.adapter.CustomerAdapter;
import mz.co.commandline.grocery.customer.delegate.CustomerDelegate;
import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.grocery.dto.GroceryDTO;


public class CustomersFragment extends BaseFragment implements SearchView.OnQueryTextListener, ClickListner<CustomerDTO> {

    @BindView(R.id.fragment_customers_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_customers_add)
    ImageView addCustomer;

    private CustomerDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_customers;
    }

    @Override
    public void onCreateView() {
        Toolbar toolBar = getToolBar();
        toolBar.inflateMenu(R.menu.search_menu);

        MenuItem menuItem = toolBar.getMenu().findItem(R.id.search_menu_action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(this);

        delegate = (CustomerDelegate) getActivity();

        addCustomer.setVisibility(delegate.addBtnVisibility());

        CustomerAdapter adapter = new CustomerAdapter(getActivity(), delegate.getCustomersDTO().getCustomerDTOs());
        adapter.setItemClickListner(this);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public String getTitle() {
        return getString(R.string.customers);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @OnClick(R.id.fragment_customers_add)
    public void onClickAddCustomerBtn() {
        delegate.addCustomer();
    }

    @Override
    public void onClickListner(CustomerDTO customerDTO) {
        delegate.selectedCustomer(customerDTO);
    }
}