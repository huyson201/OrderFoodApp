package com.edu.vn.orderfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageButton;

import com.edu.vn.orderfoodapp.apdapters.CartViewPagerAdapter;
import com.edu.vn.orderfoodapp.fragments.ListCartItemFragment;
import com.edu.vn.orderfoodapp.fragments.OrderedFragment;
import com.edu.vn.orderfoodapp.models.Bill;
import com.edu.vn.orderfoodapp.models.Food;
import com.edu.vn.orderfoodapp.models.Invoice;
import com.edu.vn.orderfoodapp.models.User;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    public static ArrayList<Invoice> invoices;
    public static ArrayList<Bill> bills;
    private ImageButton backBtn;
    private CartViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences sharedPref;
    public static String CART_TAG = "cart";
    public static final String CART_TAB_TAG = "Cart";
    public static final String INVOICES_TAG = "invoices";
    public static final String ORDERED_TAB_TAG = "Ordered";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);

        backBtn = findViewById(R.id.back_btn);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabLayout);
        invoices = new ArrayList<Invoice>();
        bills = new ArrayList<Bill>();

        adapter = new CartViewPagerAdapter(getSupportFragmentManager());

        // back btn processing event
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // get invoices
        SharedPreferences sharedPref = getSharedPreferences(CART_TAG, MODE_PRIVATE);
        String strInvoices = sharedPref.getString(INVOICES_TAG, "");
        if(strInvoices != ""){
            invoices = new Gson().fromJson(strInvoices, new TypeToken<ArrayList<Invoice>>(){}.getType());
        }


        // add fragment
        ListCartItemFragment fragment = new ListCartItemFragment(this);
        OrderedFragment fragment1 = new OrderedFragment(this);




        adapter.addFragment(fragment, CART_TAB_TAG);
        adapter.addFragment(fragment1, ORDERED_TAB_TAG);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

}