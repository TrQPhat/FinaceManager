package com.example.financemanager.Utils;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class FragmentUtils {

    public static void replaceFragment(FragmentManager fragmentManager, int layout, Fragment fragment) {
        fragmentManager.beginTransaction().replace(layout, fragment).commit();
    }
}
