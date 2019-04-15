package com.nazar.prototypeproofn.helpers;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class FragmentHelper {
    private FragmentManager fragmentManager;
    private static FragmentHelper fragmentHelper;

    public static FragmentHelper getInstance(FragmentManager fragmentManager) {
        if (fragmentHelper == null) {
            fragmentHelper = new FragmentHelper();
        }
        fragmentHelper.setFragmentManager(fragmentManager);
        return fragmentHelper;
    }

    public void replaceFragment(int container, Fragment fragment, boolean addBackToStack, String tag) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(tag);
        }
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(container, fragment, tag);
        ft.commit();
    }

    public void addFragment(int container, Fragment fragment, boolean addBackToStack, String tag) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(tag);
        }
        ft.add(container, fragment);
        ft.commit();
    }

    public void replaceFragment(int container, Fragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (addBackToStack) {
            ft.addToBackStack(fragment.getTag());
        }
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(container, fragment);
        ft.commit();

    }

    public void addFragment(int container, Fragment fragment, boolean addBackToStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addBackToStack) {
            ft.addToBackStack(fragment.getTag());
        }
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.add(container, fragment);
        ft.commit();
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
