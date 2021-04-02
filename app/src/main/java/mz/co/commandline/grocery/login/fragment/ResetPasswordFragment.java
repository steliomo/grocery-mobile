package mz.co.commandline.grocery.login.fragment;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.login.delegate.LoginDelegate;
import mz.co.commandline.grocery.util.TextInputLayoutUtil;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;

public class ResetPasswordFragment extends BaseFragment {

    @BindView(R.id.fragment_reset_password_username)
    TextInputLayout registedNumber;

    private LoginDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_reset_password;
    }

    @Override
    public void onCreateView() {
        delegate = (LoginDelegate) getActivity();
    }


    @OnClick(R.id.fragment_reset_password_reset_btn)
    public void onClickResetPasswordBtn() {
        Validator validator = new DefaultValidator(registedNumber);

        if (!validator.isValid()) {
            return;
        }

        delegate.resetPassword(TextInputLayoutUtil.getInpuText(registedNumber));
    }
}
