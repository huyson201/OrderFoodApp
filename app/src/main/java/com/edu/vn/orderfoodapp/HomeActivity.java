package com.edu.vn.orderfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.edu.vn.orderfoodapp.apdapters.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity {
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
        navigation=findViewById(R.id.AHBottomNavigation);
        navigationViewPager=findViewById(R.id.AHBottomNavigationViewPager);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        navigationViewPager.setAdapter(viewPagerAdapter);
        navigationViewPager.setPagingEnabled(true);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.titleItemHome, R.drawable.ic_baseline_home_24, R.color.color_orange);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.titleItemCart, R.drawable.ic_baseline_shopping_cart_24, R.color.blue);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.titleItemProfile, R.drawable.ic_baseline_perm_contact_calendar_24, R.color.purple_200);

        // Add items
        navigation.addItem(item1);
        navigation.addItem(item2);
        navigation.addItem(item3);

        navigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                navigationViewPager.setCurrentItem(position);
                return false;
            }
        });

        navigationViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }


}