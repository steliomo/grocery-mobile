package mz.co.commandline.grocery.customer.fragment;

import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.customer.delegate.CustomerDelegate;
import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.util.TextInputLayoutUtil;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.PhoneNumberValidator;
import mz.co.commandline.grocery.validator.Validator;


public class RegistCustomerFragment extends BaseFragment {

    @BindView(R.id.fragment_regist_customer_name)
    TextInputLayout name;

    @BindView(R.id.fragment_regist_customer_address)
    TextInputLayout address;

    @BindView(R.id.fragment_regist_customer_contact)
    TextInputLayout contact;

    @BindView(R.id.fragment_regist_customer_email)
    TextInputLayout email;

    private CustomerDelegate delegate;

    private List<Validator> validators;

    @Override
    public int getResourceId() {
        return R.layout.fragment_regist_customer;
    }

    @Override
    public void onCreateView() {
        delegate = (CustomerDelegate) getActivity();
        validators = new ArrayList<>();

        validators.add(new DefaultValidator(name));
        validators.add(new DefaultValidator(address));
        validators.add(new PhoneNumberValidator(contact));
    }

    @Override
    public String getTitle() {
        return getString(R.string.regist_customer);
    }

    @OnClick(R.id.fragment_regist_customer_regist_btn)
    public void onClickRegistBtn() {

        for (Validator validator : validators) {
            if (!validator.isValid()) {
                return;
            }
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(TextInputLayoutUtil.getInpuText(name));
        customerDTO.setAddress(TextInputLayoutUtil.getInpuText(address));
        customerDTO.setContact(TextInputLayoutUtil.getInpuText(contact));
        customerDTO.setEmail(TextInputLayoutUtil.getInpuText(email));

        delegate.registCustomer(customerDTO);
    }
}