package mz.co.commandline.grocery.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.infra.GroceryApplication;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    protected GroceryApplication application;

    private FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (GroceryApplication) getApplication();

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public abstract int getActivityFrameLayoutId();

    public void showFragment(Fragment fragment, boolean onStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(getActivityFrameLayoutId(), fragment);

        if (onStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    public void resetFragment() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void popBackStack() {

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return;
        }

        finish();
    }
}
