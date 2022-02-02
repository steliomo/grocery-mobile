package mz.co.commandline.grocery.rent.fragment;

import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.rent.delegate.RentDelegate;
import mz.co.commandline.grocery.rent.dto.RentDTO;
import mz.co.commandline.grocery.util.FormatterUtil;


public class RegistRentFragment extends BaseFragment {

    @BindView(R.id.fragment_regist_rent_date)
    TextView rentDate;

    @BindView(R.id.fragment_regist_rent_items)
    TextView totalItems;

    @BindView(R.id.fragment_regist_rent_total_discount)
    TextView discount;

    @BindView(R.id.fragment_regist_rent_total)
    TextView totalRent;

    @BindView(R.id.fragment_regist_rent_client_name)
    TextView customerName;

    @BindView(R.id.fragment_regist_rent_client_address)
    TextView customerAddress;

    @BindView(R.id.fragment_regist_rent_client_contact)
    TextView customerContact;

    private RentDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_regist_rent;
    }

    @Override
    public void onCreateView() {
        delegate = (RentDelegate) getActivity();

        RentDTO rent = delegate.getRent();
        rentDate.setText(rent.getRentDate());
        totalItems.setText(String.valueOf(rent.getRentItemsDTO().size()));
        discount.setText(FormatterUtil.mtFormat(rent.getDiscount()));
        totalRent.setText(FormatterUtil.mtFormat(rent.getTotal()));
        customerName.setText(rent.getCustomerDTO().getName());
        customerAddress.setText(rent.getCustomerDTO().getAddress());
        customerContact.setText(rent.getCustomerDTO().getContact());
    }

    @Override
    public String getTitle() {
        return getString(R.string.regist_rent);
    }

    @OnClick(R.id.fragment_regist_rent_btn)
    public void onClickRegistRent() {
        delegate.registRent();
    }
}