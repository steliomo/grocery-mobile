package mz.co.commandline.grocery.generics.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
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
