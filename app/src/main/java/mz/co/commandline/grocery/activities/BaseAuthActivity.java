package mz.co.commandline.grocery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.user.dto.UserRole;
import mz.co.commandline.grocery.user.service.UserService;

public abstract class BaseAuthActivity extends BaseActivity {

    @Inject
    UserService userService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        if (!userService.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        onGroceryCreate(savedInstanceState);
    }

    public abstract void onGroceryCreate(Bundle bundle);

    public boolean hasRole(UserRole userRole) {

        if (userRole.equals(userService.getGroceryUser().getUserRole())) {
            return true;
        }

        return false;
    }
}
