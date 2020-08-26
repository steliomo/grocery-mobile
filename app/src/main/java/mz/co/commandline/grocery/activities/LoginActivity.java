package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.grocery.dto.GroceryDTO;
import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.login.delegate.LoginDelegate;
import mz.co.commandline.grocery.login.fragment.LoginFragment;
import mz.co.commandline.grocery.login.fragment.ResetPasswordFragment;
import mz.co.commandline.grocery.login.fragment.SignUpGroceryFragment;
import mz.co.commandline.grocery.login.fragment.SignUpUserFragment;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.user.dto.UserDTO;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.FragmentUtil;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;
import mz.co.commandline.grocery.util.alert.AlertListner;
import mz.co.commandline.grocery.util.alert.AlertType;

import static mz.co.commandline.grocery.util.FragmentUtil.displayFragment;

public class LoginActivity extends BaseActivity implements LoginDelegate {

    @Inject
    UserService userService;

    private FragmentManager fragmentManager;

    private ProgressDialog progressBar;

    private AlertDialogManager dialogManager;

    private UserDTO user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        fragmentManager = getSupportFragmentManager();
        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        dialogManager = new AlertDialogManager(this);

        displayFragment(fragmentManager, R.id.login_activity_frame_layout, new LoginFragment(), Boolean.FALSE);
    }

    @Override
    public void login(String username, String password) {
        progressBar.show();

        userService.login(username, password, new ResponseListner<UserDTO>() {
            @Override
            public void success(UserDTO response) {
                progressBar.dismiss();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.bad_credentials), null);
            }
        });

    }

    @Override
    public void resetPasswordPage() {
        displayFragment(fragmentManager, R.id.login_activity_frame_layout, new ResetPasswordFragment(), Boolean.TRUE);
    }

    @Override
    public void resetPassword(String username) {
        progressBar.show();

        UserDTO user = new UserDTO(username, "");
        userService.resetPassword(user, new ResponseListner<UserDTO>() {
            @Override
            public void success(UserDTO response) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.password_was_reseted) + " " + response.getEmail(), new AlertListner() {
                    @Override
                    public void perform() {
                        FragmentUtil.popBackStack(fragmentManager, LoginActivity.this);
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.username_not_exist), new AlertListner() {
                    @Override
                    public void perform() {
                        FragmentUtil.popBackStack(fragmentManager, LoginActivity.this);
                    }
                });
            }
        });
    }

    @Override
    public void signUp() {
        displayFragment(fragmentManager, R.id.login_activity_frame_layout, new SignUpUserFragment(), Boolean.TRUE);
    }

    @Override
    public void signUpNext(UserDTO user) {
        this.user = user;
        displayFragment(fragmentManager, R.id.login_activity_frame_layout, new SignUpGroceryFragment(), Boolean.TRUE);
    }

    @Override
    public void signUp(GroceryDTO grocery) {
        this.user.getGroceryUserDTO().setGroceryDTO(grocery);
        progressBar.show();
        userService.signUp(user, new ResponseListner<UserDTO>() {
            @Override
            public void success(UserDTO userDTO) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.SUCCESS, getString(R.string.welcome) + " " + userDTO.getEmail(), new AlertListner() {
                    @Override
                    public void perform() {
                        cancel();
                    }
                });
            }

            @Override
            public void error(String message) {
                progressBar.dismiss();
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_on_sign_up), new AlertListner() {
                    @Override
                    public void perform() {
                        cancel();
                    }
                });
            }
        });
    }

    @Override
    public void cancel() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
