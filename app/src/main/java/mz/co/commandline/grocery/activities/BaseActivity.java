package mz.co.commandline.grocery.activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.ButterKnife;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.dialog.ProgressDialogManager;
import mz.co.commandline.grocery.infra.GroceryApplication;
import mz.co.commandline.grocery.util.alert.AlertDialogManager;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    protected GroceryApplication application;

    private FragmentManager fragmentManager;

    protected ProgressDialog progressBar;

    protected AlertDialogManager dialogManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (GroceryApplication) getApplication();

        fragmentManager = getSupportFragmentManager();

        ProgressDialogManager progressDialogManager = new ProgressDialogManager(this);
        progressBar = progressDialogManager.getProgressBar(getString(R.string.wait), getString(R.string.processing_request));

        dialogManager = new AlertDialogManager(this);
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
            transaction.addToBackStack(fragment.getClass().getName());
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
