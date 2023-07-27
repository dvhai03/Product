package com.example.apiproduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.apiproduct.Adapter.LoginAdapter;
import com.example.apiproduct.View.HomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

  public static String Apidress ="http://192.168.1.190:3000/";// ở nhà
//    public static String Apidress ="http://192.168.137.116:3000/";// ở trường
    TabLayout tabLayout;
    ViewPager viewPager;

    FloatingActionButton fb,gg,tw;
    float v = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", "");

        if (!authToken.isEmpty()) {
            Intent i = new Intent(getBaseContext(), HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("token",authToken);
            i.putExtra("data",bundle);
            startActivity(i);
        }

        fb=findViewById(R.id.fl_fb);
        gg = findViewById(R.id.fl_gg);
        tw = findViewById(R.id.fl_tw);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("SigUp"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        fb.setTranslationY(300);
        gg.setTranslationY(300);
        tw.setTranslationY(300);

        fb.setAlpha(v);
        gg.setAlpha(v);
        tw.setAlpha(v);

        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2500).start();
        gg.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2000).start();
        tw.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(3000).start();

    }
}