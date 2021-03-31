package mz.co.commandline.grocery.generics.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import mz.co.commandline.grocery.activities.BaseAuthActivity;
import mz.co.commandline.grocery.user.dto.UserRole;

public abstract class BaseFragment extends Fragment {

    private BaseAuthActivity activity;

    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getResourceId(), container, false);

        ButterKnife.bind(this, view);

        if (getActivity() instanceof BaseAuthActivity) {
            activity = (BaseAuthActivity) getActivity();
            toolbar = activity.getToolbar();
            toolbar.getMenu().clear();
            toolbar.setTitle(getTitle());
        }

        onCreateView();

        return view;
    }

    public abstract int getResourceId();

    public abstract void onCreateView();

    public boolean hasRole(UserRole userRole) {
        return activity.hasRole(userRole);
    }

    public Toolbar getToolBar() {
        return toolbar;
    }

    public String getTitle() {
        return "Base Fragment";
    }
}
