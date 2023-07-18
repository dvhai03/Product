package com.example.apiproduct.Adapter;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.apiproduct.View.LoginTabFragment;
import com.example.apiproduct.View.SigUpFragment;


public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;

    int totalTabs;

    public LoginAdapter(FragmentManager fm, Context context,int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public Fragment getItem(int i){
        switch (i){
            case 0 :
                LoginTabFragment loginTabFragment = new LoginTabFragment();
                return  loginTabFragment;

            case 1 :
                SigUpFragment sigUpFragment = new SigUpFragment();
                return sigUpFragment;

            default:
                return null;
        }
    }

}
