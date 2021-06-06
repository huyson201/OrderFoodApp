package com.edu.vn.orderfoodapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.edu.vn.orderfoodapp.Delegate.ClickCartItemDelegate;
import com.edu.vn.orderfoodapp.apdapters.CartItemAdapter;
import com.edu.vn.orderfoodapp.apdapters.CartViewPagerAdapter;
import com.edu.vn.orderfoodapp.fragments.ListCartItemFragment;
import com.edu.vn.orderfoodapp.fragments.OrderedFragment;
import com.edu.vn.orderfoodapp.models.Bill;
import com.edu.vn.orderfoodapp.models.Food;
import com.edu.vn.orderfoodapp.models.Invoice;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CartFragment extends Fragment {


    private View view;
    private HomeActivity homeActivity;
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
    public CartFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.cart_layout, container, false);
        homeActivity = (HomeActivity) getActivity();

        backBtn = view.findViewById(R.id.back_btn);
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tabLayout);
        invoices = new ArrayList<Invoice>();
        bills = new ArrayList<Bill>();

        adapter = new CartViewPagerAdapter(getChildFragmentManager());

//        // back btn processing event
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });


        // add fragment
        ListCartItemFragment fragment = new ListCartItemFragment(homeActivity);
        OrderedFragment fragment1 = new OrderedFragment(homeActivity);
        adapter.addFragment(fragment, CART_TAB_TAG);
        adapter.addFragment(fragment1, ORDERED_TAB_TAG);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}