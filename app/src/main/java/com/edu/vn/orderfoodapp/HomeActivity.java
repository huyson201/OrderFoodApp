package com.edu.vn.orderfoodapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.edu.vn.orderfoodapp.apdapters.ViewPagerAdapter;
import com.edu.vn.orderfoodapp.models.Invoice;
import com.edu.vn.orderfoodapp.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    public static final int EDIT_CODE = 3;
    //Fragment
    //Navigation
    private AHBottomNavigation navigation;
    private AHBottomNavigationViewPager navigationViewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        //Khoi tao doi tuong
        navigation = findViewById(R.id.AHBottomNavigation);
        navigationViewPager = findViewById(R.id.AHBottomNavigationViewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        navigationViewPager.setAdapter(viewPagerAdapter);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.titleItemHome, R.drawable.ic_baseline_home_24, R.color.color_orange);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.titleItemCart, R.drawable.ic_baseline_shopping_cart_24, R.color.blue);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.titleItemUser, R.drawable.ic_baseline_perm_contact_calendar_24, R.color.purple_200);

        // Add items
        navigation.addItem(item1);
        navigation.addItem(item2);
        navigation.addItem(item3);


        if (FoodDetailActivity.CHECK_TRANFORM) {
            navigation.setCurrentItem(1);
            navigationViewPager.setCurrentItem(1);

        }
        if (EditProfileActivity.CHECK_EDIT) {
            navigation.setCurrentItem(2);
            navigationViewPager.setCurrentItem(2);
        }

        navigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            //set mau tab khi tab duoc chon
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                navigationViewPager.setCurrentItem(position);
                return true;
            }
        });

        navigationViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                    navigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}