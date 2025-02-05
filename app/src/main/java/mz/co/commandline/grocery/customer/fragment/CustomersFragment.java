package mz.co.commandline.grocery.customer.fragment;

import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

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


public class CustomersFragment extends BaseFragment implements SearchView.OnQueryTextListener, ClickListner<CustomerDTO> {

    @BindView(R.id.fragment_customers_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_customers_add)
    ImageView addCustomer;

    private CustomerDelegate delegate;

    private CustomerAdapter adapter;

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

        adapter = new CustomerAdapter(getActivity(), delegate.getCustomersDTO().getCustomerDTOs());
        adapter.setItemClickListner(this);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    if (delegate.getCustomersDTO().getTotalCustomers() != delegate.getCustomersDTO().getCustomerDTOs().size()) {
                        if (layoutManager.findLastCompletelyVisibleItemPosition() == delegate.getCustomersDTO().getCustomerDTOs().size() - 1) {
                            delegate.updateData(adapter);
                        }
                    }

                }
            }
        });
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
    public boolean onQueryTextChange(String query) {
        List<CustomerDTO> filteredCustomers = new ArrayList<>();

        for (CustomerDTO customerDTO : delegate.getCustomersDTO().getCustomerDTOs()) {
            String searchedData = (customerDTO.getName() + customerDTO.getContact()).toLowerCase();
            if (!searchedData.contains(query.toLowerCase())) {
                filteredCustomers.add(customerDTO);
            }
        }

        delegate.getCustomersDTO().getCustomerDTOs().removeAll(filteredCustomers);
        adapter.notifyDataSetChanged();

        return true;
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