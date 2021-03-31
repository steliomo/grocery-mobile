package mz.co.commandline.grocery.login.fragment;

import android.support.design.widget.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.login.delegate.SignUpDelegate;
import mz.co.commandline.grocery.user.dto.UserDTO;
import mz.co.commandline.grocery.util.TextInputLayoutUtil;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;

public class SignUpUserFragment extends BaseFragment {

    @BindView(R.id.fragment_sign_up_user_fullname)
    TextInputLayout fullName;

    @BindView(R.id.fragment_sign_up_user_username)
    TextInputLayout username;

    @BindView(R.id.fragment_sign_up_user_password)
    TextInputLayout password;

    @BindView(R.id.fragment_sign_up_user_email)
    TextInputLayout email;

    private SignUpDelegate delegate;

    private List<Validator> validators;

    @Override
    public int getResourceId() {
        return R.layout.fragment_sign_up_user;
    }

    @Override
    public void onCreateView() {
        delegate = (SignUpDelegate) getActivity();
        validators = new ArrayList<>();
        validators.addAll(Arrays.asList(new DefaultValidator(fullName), new DefaultValidator(username), new DefaultValidator(password), new DefaultValidator(email)));
    }

    @OnClick(R.id.fragment_sign_up_user_next_btn)
    public void onClickNextBtn() {

        for (Validator validator : validators) {
            if (!validator.isValid()) {
                return;
            }
        }

        UserDTO user = new UserDTO(TextInputLayoutUtil.getInpuText(fullName), TextInputLayoutUtil.getInpuText(username), TextInputLayoutUtil.getInpuText(password), TextInputLayoutUtil.getInpuText(email));

        delegate.signUpNext(user);
    }

    @Override
    public String getTitle() {
        return getString(R.string.user);
    }
}
