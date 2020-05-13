package mz.co.commandline.grocery.login.fragment;

import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.login.delegate.LoginDelegate;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;


public class LoginFragment extends BaseFragment {

    @BindView(R.id.fragment_login_username)
    TextInputLayout username;

    @BindView(R.id.fragment_login_password)
    TextInputLayout password;

    private List<Validator> validators;

    private LoginDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onCreateView() {
        delegate = (LoginDelegate) getActivity();

        validators = new ArrayList<>();
        validators.add(new DefaultValidator(username));
        validators.add(new DefaultValidator(password));
    }

    @OnClick(R.id.fragment_login_btn)
    public void onClickLoginBtn() {

        for (Validator validator : validators) {
            if (!validator.isValid()) {
                return;
            }
        }

        delegate.login(getText(username), getText(password));
    }

    private String getText(TextInputLayout textInputLayout) {
        return textInputLayout.getEditText().getText().toString();
    }
}
