package mz.co.commandline.grocery.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseAuthActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onGroceryCreate(savedInstanceState);
    }

    public abstract void onGroceryCreate(Bundle bundle);
}
