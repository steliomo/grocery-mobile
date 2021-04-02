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


public class ApproveInventoryFragment extends BaseFragment {

    @BindView(R.id.fragment_approve_inventory_start_date)
    TextView startDate;

    @BindView(R.id.fragment_approve_inventory_status)
    TextView inventoryStatus;

    @BindView(R.id.fragment_approve_inventory_recycler_view)
    RecyclerView recyclerView;


    private InventoryDelegate inventoryDelegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_approve_inventory;
    }

    @Override
    public void onCreateView() {
        inventoryDelegate = (InventoryDelegate) getActivity();
        InventoryDTO inventoryDTO = inventoryDelegate.getInventoryDTO();

        startDate.setText(inventoryDTO.getInventoryDate());
        inventoryStatus.setText(getString(inventoryDTO.getInventoryStatus().getValue()));

        StockInventoryAdapter adapter = new StockInventoryAdapter(getActivity(), inventoryDTO.getStockInventoriesDTO());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fragment_approve_inventory_aprove_btn)
    public void onClickApproveBtn() {
        inventoryDelegate.approveInventory();
    }

    @Override
    public String getTitle() {
        return getString(R.string.approve_inventory);
    }
}
