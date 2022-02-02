package mz.co.commandline.grocery.rent.fragment;

import android.view.View;
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
import mz.co.commandline.grocery.validator.DateValidator;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;


public class AddRentItemFragment extends BaseFragment {

    @BindView(R.id.fragment_add_rent_item_image_view)
    ImageView rentItemIcon;

    @BindView(R.id.fragment_add_rent_item_name)
    TextView itemName;

    @BindView(R.id.fragment_add_rent_item_quantity)
    TextInputLayout quantity;

    @BindView(R.id.fragment_add_rent_item_start_date)
    TextInputLayout startDate;

    @BindView(R.id.fragment_add_rent_item_end_date)
    TextInputLayout endDate;

    @BindView(R.id.fragment_add_rent_item_days)
    TextInputLayout days;

    @BindView(R.id.celebrateContractMonthyValue)
    TextInputLayout value;

    @BindView(R.id.fragment_add_rent_item_discount)
    TextInputLayout discount;

    @BindView(R.id.fragment_add_rent_item_description)
    TextInputLayout description;

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

        validadors = new ArrayList<>();

        discount.getEditText().setText(String.valueOf(rentItem.getDiscount()));

        validadors.add(new DefaultValidator(quantity));
        validadors.add(new DefaultValidator(days));
        validadors.add(new DefaultValidator(value));
        validadors.add(new DefaultValidator(discount));

        configureStartDate();
        configureEndDate();
    }

    private void configureEndDate() {

        DateValidator validator = new DateValidator(getActivity(), endDate, Boolean.FALSE, Boolean.TRUE);
        validadors.add(validator);

        endDate.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    String localValue = endDate.getEditText().getText().toString();

                    if (localValue.isEmpty()) {
                        days.setEnabled(true);
                        value.setEnabled(true);
                        return;
                    }

                    rentItem.setQuantity(new BigDecimal(TextInputLayoutUtil.getInpuText(quantity)));
                    rentItem.setStartDate(TextInputLayoutUtil.getInpuText(startDate));
                    rentItem.setEndDate(TextInputLayoutUtil.getInpuText(endDate));

                    if (!rentItem.isEndDateValid()) {
                        endDate.setError(getString(R.string.start_date_connot_be_greater_than_or_equal_the_end_date));
                        return;
                    }

                    endDate.setError(null);
                    endDate.setErrorEnabled(Boolean.FALSE);

                    rentItem.setDays();

                    days.getEditText().setText(String.valueOf(rentItem.getDays()));
                    days.setEnabled(Boolean.FALSE);

                    rentItem.calculateValue();
                    value.getEditText().setText(String.valueOf(rentItem.getValue()));
                    value.setEnabled(Boolean.FALSE);
                }
            }
        });
    }

    private void configureStartDate() {

        DateValidator validator = new DateValidator(getActivity(), startDate, Boolean.FALSE, Boolean.TRUE);
        validadors.add(validator);

        startDate.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    String startdDateValue = TextInputLayoutUtil.getInpuText(startDate);
                    String endDateValue = TextInputLayoutUtil.getInpuText(endDate);

                    if (startdDateValue == null) {
                        return;
                    }

                    rentItem.setStartDate(startdDateValue);

                    if (endDateValue == null) {
                        return;
                    }

                    rentItem.setDays();

                    days.getEditText().setText(String.valueOf(rentItem.getDays()));
                    rentItem.calculateValue();
                    value.getEditText().setText(String.valueOf(rentItem.getValue()));
                }
            }
        });
    }

    @Override
    public String getTitle() {
        return getString(R.string.add_item);
    }

    @OnClick(R.id.celebrateContractBtn)
    public void onClickAddRentItem() {

        for (Validator validator : validadors) {
            if (!validator.isValid()) {
                return;
            }
        }

        if (!rentItem.isEndDateValid()) {
            endDate.setError(getString(R.string.start_date_connot_be_greater_than_or_equal_the_end_date));
            return;
        }
        rentItem.setQuantity(new BigDecimal(TextInputLayoutUtil.getInpuText(quantity)));
        rentItem.setDiscount(new BigDecimal(TextInputLayoutUtil.getInpuText(discount)));
        rentItem.setDescription(TextInputLayoutUtil.getInpuText(description));
        rentItem.calculateValue();

        delegte.addRentItem(rentItem);
    }
}