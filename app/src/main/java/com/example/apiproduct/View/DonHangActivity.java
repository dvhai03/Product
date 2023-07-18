package com.example.apiproduct.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.apiproduct.Adapter.DonHangAdapter;
import com.example.apiproduct.R;
import com.google.android.material.tabs.TabLayout;

public class DonHangActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);

        ImageView back = findViewById(R.id.icon_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DonHangActivity.this.finish();
            }
        });

        tabLayout = findViewById(R.id.id_tablayout);
        viewPager = findViewById(R.id.pager);
        tabLayout.addTab(tabLayout.newTab().setText("Chờ Xác Nhận"));
        tabLayout.addTab(tabLayout.newTab().setText("Đang Giao"));
        tabLayout.addTab(tabLayout.newTab().setText("Đã Giao"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final DonHangAdapter adapter = new DonHangAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }
}