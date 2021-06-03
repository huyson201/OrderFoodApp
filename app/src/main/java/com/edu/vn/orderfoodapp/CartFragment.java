package com.edu.vn.orderfoodapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

//    //properties
//    private CheckBox chkAll;
//    private boolean isCheckAll = false;
//    private RecyclerView listCartItems;
//    private ArrayList<Invoice> invoices;
//    private CartItemAdapter adapter;
//
//    private TextView lblTotalPrice;
//    private ImageButton backBtn;
//    private Button buyBtn;
//
    private View view;
    private HomeActivity homeActivity;
//
//    public static String CART_TAG = "cart";

    public CartFragment() {
        // Required empty public constructor
    }


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

        adapter = new CartViewPagerAdapter(homeActivity.getSupportFragmentManager());

//        // back btn processing event
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });


        // get invoices
        SharedPreferences sharedPref = homeActivity.getSharedPreferences(CART_TAG, Context.MODE_PRIVATE);
        String strInvoices = sharedPref.getString(INVOICES_TAG, "");
        if(strInvoices != ""){
            invoices = new Gson().fromJson(strInvoices, new TypeToken<ArrayList<Invoice>>(){}.getType());
        }


        // add fragment
        ListCartItemFragment fragment = new ListCartItemFragment(homeActivity);
        OrderedFragment fragment1 = new OrderedFragment(homeActivity);




        adapter.addFragment(fragment, CART_TAB_TAG);
        adapter.addFragment(fragment1, ORDERED_TAB_TAG);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
//        chkAll = view.findViewById(R.id.chkAll);
//        invoices = new ArrayList<Invoice>();
//        listCartItems = view.findViewById(R.id.list_cart_item);
//        lblTotalPrice = view.findViewById(R.id.lbl_total_price);
//        backBtn = view.findViewById(R.id.back_btn);
//        buyBtn = view.findViewById(R.id.buy_btn);

    //Chua dung do da dung fragment
    // back btn processing event
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPress();
//            }
//        });

    // check all processing
//        chkAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isCheckAll = ((CheckBox) v).isChecked();
//                for(Invoice invoice : invoices){
//                    invoice.setSelected(isCheckAll);
//                }
//
//                adapter.notifyDataSetChanged();
//
//                // get sum price
//                int sumPrice = getSumPrice(invoices);
//                lblTotalPrice.setText(sumPrice + "");
//
//            }
//        });

    // add invoice
//        Food food = new Food("1","data:image/jpeg;base64,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","Thịt bò xào", "thịt bò xào thơm ngon", 25000);
//        invoices.add(new Invoice("1", food,1));
//        invoices.add(new Invoice("2", food,1));
//        invoices.add(new Invoice("3", food,1));
//        invoices.add(new Invoice("4", food,1));
//        invoices.add(new Invoice("5", food,1));

//        adapter = new CartItemAdapter(homeActivity, invoices);
//        adapter.setClickAllDelegate(this);
//        //init linear layout manager
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(homeActivity);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        // set for recycler view
//        listCartItems.setAdapter(adapter);
//        listCartItems.setLayoutManager(linearLayoutManager);
//
//        return view;
//    }
//
//    // calculate sum price of invoices
//    public int getSumPrice(ArrayList<Invoice> invoices){
//        int sum = 0;
//        for(Invoice invoice : invoices){
//            if(invoice.isSelected()){
//                sum += invoice.getQuantity() * invoice.getFoods().getFoodPrice();
//            }
//        }
//        return sum;
//    }
//
//    @Override
//    public void onClickCartItem() {
//        int sumPrice = getSumPrice(invoices);
//        lblTotalPrice.setText(sumPrice + "");
//    }
}