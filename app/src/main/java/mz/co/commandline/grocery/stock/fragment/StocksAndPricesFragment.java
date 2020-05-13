package mz.co.commandline.grocery.stock.fragment;

import android.support.design.widget.TextInputLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.stock.delegate.StockDelegate;
import mz.co.commandline.grocery.stock.delegate.UpdateStockDelegate;
import mz.co.commandline.grocery.stock.dto.StockDTO;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;

public class StocksAndPricesFragment extends BaseFragment {

    @BindView(R.id.fragment_stocks_and_prices_product_name)
    TextView productName;

    @BindView(R.id.fragment_stocks_and_prices_purchase_price)
    TextInputLayout purchasePrice;

    @BindView(R.id.fragment_stocks_and_prices_sale_price)
    TextInputLayout salePrice;

    @BindView(R.id.fragment_stocks_and_prices_quantity)
    TextInputLayout quantity;

    @BindView(R.id.fragment_add_inventory_minimum_stock)
    TextInputLayout minimumStock;

    private UpdateStockDelegate updateStockDelegate;

    private StockDTO stockDTO;

    private List<Validator> validators;

    private StockDelegate stockDelegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_stocks_and_prices;
    }

    @Override
    public void onCreateView() {
        updateStockDelegate = (UpdateStockDelegate) getActivity();
        stockDelegate = (StockDelegate) getActivity();

        stockDTO = stockDelegate.getStock();
        productName.setText(stockDTO.getProductDescriptionDTO().getName());

        purchasePrice.getEditText().setText(stockDTO.getPurchasePrice().toString());
        salePrice.getEditText().setText(stockDTO.getSalePrice().toString());
        quantity.getEditText().setText(stockDTO.getQuantity().toString());
        minimumStock.getEditText().setText(stockDTO.getMinimumStock().toString());

        validateFields();
    }

    private void validateFields() {
        validators = new ArrayList<>();
        validators.add(new DefaultValidator(purchasePrice));
        validators.add(new DefaultValidator(salePrice));
        validators.add(new DefaultValidator(quantity));
        validators.add(new DefaultValidator(minimumStock));
    }

    @OnClick(R.id.fragment_stocks_and_prices_cancel)
    public void onClickCancel() {
        updateStockDelegate.cancel();
    }

    @OnClick(R.id.fragment_stocks_and_prices_add)
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

        updateStockDelegate.addStockItem(stockDTO);
    }

    @Override
    public String getTitle() {
        return getString(R.string.add_stocks);
    }
}
