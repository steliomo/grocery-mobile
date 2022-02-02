package mz.co.commandline.grocery.inventory.fragment;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.inventory.adapter.StockInventoryAdapter;
import mz.co.commandline.grocery.inventory.delegate.InventoryDelegate;
import mz.co.commandline.grocery.inventory.dto.InventoryDTO;
import mz.co.commandline.grocery.item.delegate.ItemDelegate;
import mz.co.commandline.grocery.item.dto.ItemType;


public class PerformInventoryFragment extends BaseFragment {

    @BindView(R.id.fragment_perform_inventory_start_date)
    TextView startDate;

    @BindView(R.id.fragment_perform_inventory_status)
    TextView status;

    @BindView(R.id.fragment_perform_inventory_recycler_view)
    RecyclerView recyclerView;

    private ItemDelegate itemDelegate;

    private InventoryDelegate inventoryDelegate;


    @Override
    public int getResourceId() {
        return R.layout.fragment_perform_inventory;
    }

    @Override
    public void onCreateView() {
        itemDelegate = (ItemDelegate) getActivity();
        inventoryDelegate = (InventoryDelegate) getActivity();

        InventoryDTO inventory = inventoryDelegate.getInventoryDTO();
        startDate.setText(inventory.getInventoryDate());
        status.setText(getString(inventory.getInventoryStatus().getValue()));

        StockInventoryAdapter adapter = new StockInventoryAdapter(getActivity(), inventory.getStockInventoriesDTO(), Boolean.TRUE);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fragment_perform_inventory_add_item)
    public void onClickAddItem() {
        itemDelegate.selectItemType(ItemType.PRODUCT);
    }

    @OnClick(R.id.fragment_perform_inventory_perform_inventory)
    public void onClickPerformInventory() {
        inventoryDelegate.performInventory();
    }

    @Override
    public String getTitle() {
        return getString(R.string.perform_inventory);
    }
}
