package com.edu.vn.orderfoodapp.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.vn.orderfoodapp.CartActivity;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.apdapters.OrderedListBillAdapter;
import com.edu.vn.orderfoodapp.models.Bill;

import java.util.ArrayList;


public class OrderedFragment extends Fragment {
    private RecyclerView recyclerView;
    private Activity context;
    private OrderedListBillAdapter adapter;
    private ArrayList<Bill> bills;

    public OrderedFragment(Activity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ordered_fragment, container, false);
        recyclerView = view.findViewById(R.id.list_ordered);
        bills = CartActivity.bills;

        adapter = new OrderedListBillAdapter(this.context, bills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        Log.d("bills", bills.toString());
        return view;
    }
}