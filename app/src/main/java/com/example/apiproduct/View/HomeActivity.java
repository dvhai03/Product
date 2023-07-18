package com.example.apiproduct.View;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.apiproduct.R;
import com.example.apiproduct.View.Fragment.HomeFragment;
import com.example.apiproduct.View.Fragment.ProFileFragment;
import com.example.apiproduct.View.Fragment.ThongBaoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
   ProFileFragment proFileFragment = new ProFileFragment();
    ThongBaoFragment thongBaoFragment = new ThongBaoFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.navigationbt);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int i =item.getItemId();
                    if(i==R.id.navigation_trangchu){
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,homeFragment).commit();
                        return true;
                    } else if (i==R.id.navigation_profile) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,proFileFragment).commit();
                        return true;
                    }else if (i==R.id.navigation_thongbao){
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,thongBaoFragment).commit();
                        return true;
                    }
                    return false;
            }
        });
    }
}