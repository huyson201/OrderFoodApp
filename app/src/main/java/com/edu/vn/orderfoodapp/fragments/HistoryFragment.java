package com.edu.vn.orderfoodapp.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.vn.orderfoodapp.CartActivity;
import com.edu.vn.orderfoodapp.HomeActivity;
import com.edu.vn.orderfoodapp.LoginActivity;
import com.edu.vn.orderfoodapp.R;
import com.edu.vn.orderfoodapp.apdapters.OrderedListBillAdapter;
import com.edu.vn.orderfoodapp.models.Bill;
import com.edu.vn.orderfoodapp.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private Activity context;
    private OrderedListBillAdapter adapter;
    private ArrayList<Bill> bills;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    private boolean clearBill = false;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_layout, container, false);
        recyclerView = view.findViewById(R.id.list_invoice);
        bills = new ArrayList<Bill>();

        // get all bill of user

        Log.d("idhihi", LoginActivity.userProFile.getId());
        db.child("bills").orderByChild("userId").equalTo(LoginActivity.userProFile.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (clearBill) {
                    bills.clear();
                    clearBill = false;
                }
                if (!snapshot.getKey().isEmpty()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Bill bill = data.getValue(Bill.class);
                        if (bill.getStatus().equalsIgnoreCase(Bill.DELIVERED_STATUS_TAG)) {
                            bills.add(bill);
                        }
                    }
                }
                context = new HomeActivity();
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