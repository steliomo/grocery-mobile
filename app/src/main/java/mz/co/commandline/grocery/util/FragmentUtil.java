package mz.co.commandline.grocery.util;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import mz.co.commandline.grocery.inventory.fragment.PerformInventoryFragment;

public class FragmentUtil {

    public static void displayFragment(FragmentManager fragmentManager, int frameLayoutId, Fragment fragment, boolean onStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameLayoutId, fragment);

        if (onStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    public static void resetFragment(FragmentManager fragmentManager) {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void popBackStack(FragmentManager fragmentManager, Activity activity) {

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return;
        }

        activity.finish();
    }
}
