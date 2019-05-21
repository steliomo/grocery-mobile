package mz.co.commandline.grocery.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import mz.co.commandline.grocery.infra.GroceryApplication;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    protected GroceryApplication application;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (GroceryApplication) getApplication();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        ButterKnife.bind(this);

        if (toolbar == null) {
            return;
        }

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }
}
