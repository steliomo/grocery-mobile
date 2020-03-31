package mz.co.commandline.grocery.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import mz.co.commandline.grocery.activities.BaseActivity;
import mz.co.commandline.grocery.activities.BaseAuthActivity;
import mz.co.commandline.grocery.infra.GroceryApplication;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.user.dto.UserRole;
import mz.co.commandline.grocery.user.service.UserService;

public abstract class BaseFragment extends Fragment {

    private BaseAuthActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getResourceId(), container, false);

        ButterKnife.bind(this, view);

        if (getActivity() instanceof BaseAuthActivity) {
            activity = (BaseAuthActivity) getActivity();
        }

        onCreateView();

        return view;
    }

    public abstract int getResourceId();

    public abstract void onCreateView();

    public boolean hasRole(UserRole userRole) {
        return activity.hasRole(userRole);
    }
}
