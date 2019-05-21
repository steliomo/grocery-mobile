package mz.co.commandline.grocery.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.user.service.UserService;

public abstract class BaseAuthActivity extends BaseActivity {

    @Inject
    UserService userService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        if(!userService.isLoggedIn()){
            userService.login("822546100", "111111");
        }

        onGroceryCreate(savedInstanceState);
    }

    public abstract void onGroceryCreate(Bundle bundle);
}
