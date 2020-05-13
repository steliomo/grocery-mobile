package mz.co.commandline.grocery.inventory.fragment;

import android.support.design.widget.TextInputLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.inventory.delegate.InventoryDelegate;
import mz.co.commandline.grocery.inventory.dto.StockInventoryDTO;
import mz.co.commandline.grocery.stock.delegate.StockDelegate;
import mz.co.commandline.grocery.stock.dto.StockDTO;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;


public class AddInventoryItemFragment extends BaseFragment {

    @BindView(R.id.fragment_add_inventory_item_product_name)
    TextView productName;

    @BindView(R.id.fragment_add_inventory_item_quantity)
    TextInputLayout quantity;

    private List<Validator> validators;

    private InventoryDelegate inventoryDelegate;

    private StockDTO stockDTO;

    @Override
    public int getResourceId() {
        return R.layout.fragment_add_inventory_item;
    }

    @Override
    public void onCreateView() {
        StockDelegate stockDelegate = (StockDelegate) getActivity();
        inventoryDelegate = (InventoryDelegate) getActivity();

        stockDTO = stockDelegate.getStock();
        productName.setText(stockDTO.getProductDescriptionDTO().getName());
        validators = new ArrayList<>();

        validators.add(new DefaultValidator(quantity));
    }

    @OnClick(R.id.fragment_add_inventory_item_add)
    public void onAddStockInventory() {

        for (Validator validator : validators) {
            if (!validator.isValid()) {
                return;
            }
        }

        StockInventoryDTO stockInventory = new StockInventoryDTO(stockDTO, quantity.getEditText().getText().toString());
        inventoryDelegate.addStockInventoryDTO(stockInventory);
    }

    @OnClick(R.id.fragment_add_inventory_item_cancel)
    public void onClickCancel() {
        inventoryDelegate.cancel();
    }

    @Override
    public String getTitle() {
        return getString(R.string.add_inventory_item);
    }
}
