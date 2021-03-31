package mz.co.commandline.grocery.login.fragment;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.dto.EnumDTO;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.login.delegate.LoginDelegate;
import mz.co.commandline.grocery.util.TextInputLayoutUtil;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;


public class SignUpGroceryFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.fragment_sign_up_grocery_spinner)
    Spinner unitType;

    @BindView(R.id.fragment_sign_up_grocery_name)
    TextInputLayout name;

    @BindView(R.id.fragment_sign_up_grocery_address)
    TextInputLayout address;

    @BindView(R.id.fragment_sign_up_grocery_phone_number)
    TextInputLayout phoneNumber;

    @BindView(R.id.fragment_sign_up_grocery_email)
    TextInputLayout email;

    private LoginDelegate delegate;

    private List<Validator> validators;

    @Override
    public int getResourceId() {
        return R.layout.fragment_sign_up_grocery;
    }

    @Override
    public void onCreateView() {

        delegate = (LoginDelegate) getActivity();

        validators = new ArrayList<>();
        validators.addAll(Arrays.asList(new DefaultValidator(name), new DefaultValidator(address), new DefaultValidator(phoneNumber)));

        ArrayAdapter<EnumDTO> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, delegate.getUnitTypes());
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        unitType.setAdapter(adapter);
        unitType.setOnItemSelectedListener(this);
    }

    @OnClick(R.id.fragment_sign_up_grocery_cancel_btn)
    public void onClickCancelBtn() {
        delegate.cancel();
    }

    @OnClick(R.id.fragment_sign_up_grocery_regist_btn)
    public void onClickRegistBtn() {

        if (delegate.getUnitType().getValue() == null) {
            ((TextView) unitType.getSelectedView()).setError(getString(R.string.required_field));
            return;
        }

        for (Validator validator : validators) {
            if (!validator.isValid()) {
                return;
            }
        }

        GroceryDTO grocery = new GroceryDTO(TextInputLayoutUtil.getInpuText(name), TextInputLayoutUtil.getInpuText(address), TextInputLayoutUtil.getInpuText(phoneNumber), TextInputLayoutUtil.getInpuText(email));
        grocery.setUnitType(delegate.getUnitType().getValue());
        delegate.signUp(grocery);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        delegate.setUnitType(delegate.getUnitTypes().get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
