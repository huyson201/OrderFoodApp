package com.edu.vn.orderfoodapp.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.vn.orderfoodapp.LoginActivity;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.apdapters.OrderedListBillAdapter;
import com.edu.vn.orderfoodapp.models.Bill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrderedFragment extends Fragment {
    private RecyclerView recyclerView;
    private Activity context;
    private OrderedListBillAdapter adapter;
    private ArrayList<Bill> bills;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    public OrderedFragment(Activity context) {
        this.context = context;
    }

    private boolean clearBill = false;

    public OrderedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ordered_fragment, container, false);
        recyclerView = view.findViewById(R.id.list_ordered);
        bills = new ArrayList<Bill>();

        // get all bill of user
        db.child("bills").orderByChild("userId").equalTo(LoginActivity.userProFile.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(clearBill){
                    bills.clear();
                    clearBill = false;
                }
                if(!snapshot.getKey().isEmpty()){
                    for(DataSnapshot data : snapshot.getChildren()){
                        Bill bill = data.getValue(Bill.class);
                        bills.add(bill);

                    }
                }

                adapter = new OrderedListBillAdapter(context, bills);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
                clearBill = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // not do anything here
            }
        });


        return view;
    }



}