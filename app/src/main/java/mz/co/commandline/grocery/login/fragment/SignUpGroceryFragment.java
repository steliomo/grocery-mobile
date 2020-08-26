package mz.co.commandline.grocery.login.fragment;

import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.login.delegate.LoginDelegate;
import mz.co.commandline.grocery.util.TextInputLayoutUtil;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;


public class SignUpGroceryFragment extends BaseFragment {

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
    }

    @OnClick(R.id.fragment_sign_up_grocery_cancel_btn)
    public void onClickCancelBtn() {
        delegate.cancel();
    }

    @OnClick(R.id.fragment_sign_up_grocery_regist_btn)
    public void onClickRegistBtn() {
        for (Validator validator : validators) {
            if (!validator.isValid()) {
                return;
            }
        }

        GroceryDTO grocery = new GroceryDTO(TextInputLayoutUtil.getInpuText(name), TextInputLayoutUtil.getInpuText(address), TextInputLayoutUtil.getInpuText(phoneNumber), TextInputLayoutUtil.getInpuText(email));

        delegate.signUp(grocery);
    }
}
