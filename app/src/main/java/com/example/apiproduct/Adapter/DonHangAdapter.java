package com.example.apiproduct.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.apiproduct.View.Fragment.ChoXacNhanFragment;
import com.example.apiproduct.View.Fragment.DaGiaoFragment;
import com.example.apiproduct.View.Fragment.DangGiaoFragment;


public class DonHangAdapter extends FragmentPagerAdapter {
    private Context context;

    int totalTabs;

    public DonHangAdapter(@NonNull FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0 :
                ChoXacNhanFragment choXacNhanFragment = new ChoXacNhanFragment();
                return  choXacNhanFragment;

            case 1 :
                DangGiaoFragment dangGiaoFragment = new DangGiaoFragment();
                return dangGiaoFragment;

            case 2 :
                DaGiaoFragment daGiaoFragment = new DaGiaoFragment();
                return  daGiaoFragment;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return  totalTabs;
    }
}
