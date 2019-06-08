package mz.co.commandline.grocery.stock.fragment;

import android.support.design.widget.TextInputLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.stock.delegate.StockDelegate;
import mz.co.commandline.grocery.stock.model.Stock;
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

    private StockDelegate delegate;

    private Stock stock;

    private List<Validator> validators;

    @Override
    public int getResourceId() {
        return R.layout.fragment_stocks_and_prices;
    }

    @Override
    public void onCreateView() {
        delegate = (StockDelegate) getActivity();
        stock = delegate.getStock();
        productName.setText(stock.getProductDescription().getName());

        purchasePrice.getEditText().setText(stock.getPurchasePrice().toString());
        salePrice.getEditText().setText(stock.getSalePrice().toString());
        quantity.getEditText().setText(stock.getQuantity().toString());

        validateFields();
    }

    private void validateFields() {
        validators = new ArrayList<>();
        validators.add(new DefaultValidator(purchasePrice));
        validators.add(new DefaultValidator(salePrice));
        validators.add(new DefaultValidator(quantity));
    }

    @OnClick(R.id.fragment_stocks_and_prices_cancel)
    public void onClickCancel() {
        delegate.cancel();
    }

    @OnClick(R.id.fragment_stocks_and_prices_add)
    public void onClickAdd() {

        for (Validator validator : validators) {
            if (!validator.isValid()) {
                return;
            }
        }

        stock.setPurchasePrice(new BigDecimal(purchasePrice.getEditText().getText().toString()));
        stock.setSalePrice(new BigDecimal(salePrice.getEditText().getText().toString()));
        stock.setQuantity(new BigDecimal(quantity.getEditText().getText().toString()));

        delegate.addStockItem(stock);
    }
}
