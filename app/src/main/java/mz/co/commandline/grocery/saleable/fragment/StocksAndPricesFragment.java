package mz.co.commandline.grocery.saleable.fragment;

import com.google.android.material.textfield.TextInputLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.saleable.delegate.SaleableItemActionDelegate;
import mz.co.commandline.grocery.saleable.dto.ActionType;
import mz.co.commandline.grocery.saleable.dto.StockDTO;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;

public class StocksAndPricesFragment extends BaseFragment {

    @BindView(R.id.fragment_saleable_service_name)
    TextView productName;

    @BindView(R.id.fragment_saleable_service_price)
    TextInputLayout purchasePrice;

    @BindView(R.id.fragment_stocks_and_prices_sale_price)
    TextInputLayout salePrice;

    @BindView(R.id.fragment_stocks_and_prices_quantity)
    TextInputLayout quantity;

    @BindView(R.id.fragment_add_inventory_minimum_stock)
    TextInputLayout minimumStock;

    private StockDTO stockDTO;

    private List<Validator> validators;

    private SaleableItemActionDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_stocks_and_prices;
    }

    @Override
    public void onCreateView() {
        delegate = (SaleableItemActionDelegate) getActivity();

        stockDTO = (StockDTO) delegate.getSaleableItem();
        productName.setText(stockDTO.getProductDescriptionDTO().getName());

        getToolBar().setTitle(delegate.getTiTle());

        validateFields();

        if (ActionType.UPDATE.equals(delegate.getActionType())) {
            purchasePrice.getEditText().setText(stockDTO.getPurchasePrice().toString());
            salePrice.getEditText().setText(stockDTO.getSalePrice().toString());
            quantity.getEditText().setText(stockDTO.getQuantity().toString());
            minimumStock.getEditText().setText(stockDTO.getMinimumStock().toString());
        }
    }

    private void validateFields() {
        validators = new ArrayList<>();
        validators.add(new DefaultValidator(purchasePrice));
        validators.add(new DefaultValidator(salePrice));
        validators.add(new DefaultValidator(quantity));
        validators.add(new DefaultValidator(minimumStock));
    }

    @OnClick(R.id.fragment_saleable_service_cancel)
    public void onClickCancel() {
        delegate.cancel();
    }

    @OnClick(R.id.fragment_saleable_service_add)
    public void onClickAdd() {

        for (Validator validator : validators) {
            if (!validator.isValid()) {
                return;
            }
        }

        stockDTO.setPurchasePrice(purchasePrice.getEditText().getText().toString());
        stockDTO.setSalePrice(salePrice.getEditText().getText().toString());
        stockDTO.setQuantity(quantity.getEditText().getText().toString());
        stockDTO.setMinimumStock(minimumStock.getEditText().getText().toString());

        delegate.addSaleableItem(stockDTO);
    }
}
