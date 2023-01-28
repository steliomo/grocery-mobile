package mz.co.commandline.grocery.rent.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.rent.delegate.RentDelegate;
import mz.co.commandline.grocery.rent.dto.RentItemDTO;
import mz.co.commandline.grocery.util.TextInputLayoutUtil;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;


public class AddRentItemFragment extends BaseFragment {

    @BindView(R.id.fragment_add_rent_item_image_view)
    ImageView rentItemIcon;

    @BindView(R.id.fragment_add_rent_item_name)
    TextView itemName;

    @BindView(R.id.fragment_add_rent_item_quantity)
    TextInputLayout quantity;

    @BindView(R.id.fragment_add_rent_item_days)
    TextInputLayout days;

    @BindView(R.id.fragment_add_rent_item_value)
    TextInputLayout value;

    @BindView(R.id.fragment_add_rent_item_discount)
    TextInputLayout discount;

    private List<Validator> validadors;

    private RentDelegate delegte;

    private RentItemDTO rentItem;

    @Override
    public int getResourceId() {
        return R.layout.fragment_add_rent_item;
    }

    @Override
    public void onCreateView() {
        delegte = (RentDelegate) getActivity();

        rentItemIcon.setImageResource(delegte.getItemType().getIconId());
        rentItem = new RentItemDTO(delegte.getSaleableItem());

        itemName.setText(rentItem.getName());

        discount.getEditText().setText(String.valueOf(rentItem.getDiscount()));

        validadors = new ArrayList<>();
        validadors.add(new DefaultValidator(quantity));
        validadors.add(new DefaultValidator(days));
        validadors.add(new DefaultValidator(discount));

        onFocusChangeListner(quantity);
        onFocusChangeListner(days);
    }

    private void onFocusChangeListner(TextInputLayout textInputLayout) {
        textInputLayout.getEditText().setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                calculatePlannedValue();
            }
        });
    }

    private void calculatePlannedValue() {
        for (Validator validator : validadors) {
            if (!validator.isValid()) {
                return;
            }
        }

        rentItem.setPlannedQuantity(new BigDecimal(TextInputLayoutUtil.getInpuText(quantity)));
        rentItem.setPlannedDays(new BigDecimal(TextInputLayoutUtil.getInpuText(days)));
        value.getEditText().setText(String.valueOf(rentItem.calculatePlannedValue()));
        value.setEnabled(Boolean.FALSE);
    }

    @Override
    public String getTitle() {
        return getString(R.string.add_item);
    }

    @OnClick(R.id.fragment_rent_item_add_btn)
    public void onClickAddRentItem() {

        for (Validator validator : validadors) {
            if (!validator.isValid()) {
                return;
            }
        }

        rentItem.setPlannedQuantity(new BigDecimal(TextInputLayoutUtil.getInpuText(quantity)));
        rentItem.setPlannedDays(new BigDecimal(TextInputLayoutUtil.getInpuText(days)));
        rentItem.setDiscount(new BigDecimal(TextInputLayoutUtil.getInpuText(discount)));

        delegte.addRentItem(rentItem);
    }
}