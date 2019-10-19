package mz.co.commandline.grocery.sale.fragment;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.sale.delegate.SaleDelegate;
import mz.co.commandline.grocery.sale.dto.SaleItemDTO;
import mz.co.commandline.grocery.stock.delegate.StockDelegate;
import mz.co.commandline.grocery.stock.dto.StockDTO;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;

public class AddSaleItemFragment extends BaseFragment {

    @BindView(R.id.fragment_add_sale_item_product_name)
    TextView productName;

    @BindView(R.id.fragment_stocks_and_prices_quantity)
    TextInputLayout quantity;

    @BindView(R.id.fragment_add_sale_item_value)
    TextInputLayout itemValue;

    @BindView(R.id.fragment_add_sale_item_discount)
    TextInputLayout discount;

    private SaleDelegate saleDelegate;

    private List<Validator> validators;

    private StockDTO stockDTO;

    @Override
    public int getResourceId() {
        return R.layout.fragment_add_sale_item;
    }

    @Override
    public void onCreateView() {
        saleDelegate = (SaleDelegate) getActivity();
        StockDelegate stockDelegate = (StockDelegate) getActivity();

        stockDTO = stockDelegate.getStock();
        productName.setText(stockDTO.getProductDescriptionDTO().getName());
        validators = new ArrayList<>();

        configureQuantityInput();
        configureItemValueInput();

        validators.add(new DefaultValidator(discount));

    }

    private void configureQuantityInput() {
        final Validator validator = new DefaultValidator(quantity);
        validators.add(validator);

        quantity.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    String value = quantity.getEditText().getText().toString();

                    if (value.isEmpty()) {
                        itemValue.setEnabled(true);
                        return;
                    }

                    BigDecimal total = new BigDecimal(stockDTO.getSalePrice()).multiply(new BigDecimal(value));
                    itemValue.getEditText().setText(total.toString());
                    itemValue.setEnabled(false);
                    validator.isValid();
                }
            }
        });
    }

    private void configureItemValueInput() {
        final Validator validator = new DefaultValidator(itemValue);
        validators.add(validator);

        itemValue.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    String value = itemValue.getEditText().getText().toString();

                    if (value.isEmpty()) {
                        quantity.setEnabled(true);
                        return;
                    }

                    BigDecimal saleItemValue = new BigDecimal(value);
                    saleItemValue = saleItemValue.divide(new BigDecimal(stockDTO.getSalePrice()), 2, RoundingMode.HALF_UP);
                    quantity.getEditText().setText(saleItemValue.toString());
                    quantity.setEnabled(false);
                    validator.isValid();
                }
            }
        });
    }

    @OnClick(R.id.fragment_add_sale_item_add)
    public void onClickAddItem() {

        for (Validator validator : validators) {
            if (!validator.isValid()) {
                return;
            }
        }

        SaleItemDTO saleItem = new SaleItemDTO(stockDTO,
                new BigDecimal(quantity.getEditText().getText().toString()),
                new BigDecimal(itemValue.getEditText().getText().toString()),
                new BigDecimal(discount.getEditText().getText().toString()));

        saleDelegate.addSaleItem(saleItem);
    }

    @OnClick(R.id.fragment_add_sale_item_cancel)
    public void onClickCancel() {
        saleDelegate.cancel();
    }
}
