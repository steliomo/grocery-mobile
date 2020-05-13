package mz.co.commandline.grocery.inventory.fragment;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.inventory.delegate.InventoryDelegate;
import mz.co.commandline.grocery.user.dto.UserRole;


public class InventoryMenuFragment extends BaseFragment {

    @BindView(R.id.fragment_inventory_menu_approve_inventory_btn)
    Button approveInventoryBtn;

    private InventoryDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_inventory_menu;
    }

    @Override
    public void onCreateView() {
        delegate = (InventoryDelegate) getActivity();

        if (hasRole(UserRole.OPERATOR)) {
            approveInventoryBtn.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.fragment_inventory_menu_perform_inventory_btn)
    public void onClickPerformInventoryBtn() {
        delegate.displayPerformInventoryFragment();
    }

    @OnClick(R.id.fragment_inventory_menu_approve_inventory_btn)
    public void onClickApproveInventoryBtn() {
        delegate.displayApproveInventoryFragment();
    }

    @Override
    public String getTitle() {
        return getString(R.string.inventory);
    }
}
