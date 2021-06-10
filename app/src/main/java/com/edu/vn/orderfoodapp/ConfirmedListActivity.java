package com.edu.vn.orderfoodapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.vn.orderfoodapp.apdapters.BillManagerAdapter;
import com.edu.vn.orderfoodapp.models.Bill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfirmedListActivity extends AppCompatActivity {
    private RecyclerView deliveryList;
    private ArrayList<Bill> bills;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private boolean clearBill = false;
    private BillManagerAdapter adapter;
    private TextView lblEmpty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_list_layout);
        lblEmpty = findViewById(R.id.lbl_empty);
        deliveryList = findViewById(R.id.delivery_list);

        lblEmpty.setVisibility(View.INVISIBLE);

        bills = new ArrayList<Bill>();

        db.child("bills").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(clearBill){
                    bills.clear();
                    clearBill = false;
                }

                if(!snapshot.getKey().isEmpty()){
                    for(DataSnapshot data : snapshot.getChildren()){
                        Bill bill = data.getValue(Bill.class);
                        if(bill.getStatus().equals(Bill.DELIVERED_STATUS_TAG)){
                            bills.add(bill);
                        }

                    }
                }

                if(bills.size() == 0){
                    lblEmpty.setVisibility(View.VISIBLE);
                    deliveryList.setVisibility(View.INVISIBLE);
                }else {
                    lblEmpty.setVisibility(View.INVISIBLE);
                    deliveryList.setVisibility(View.VISIBLE);

                    adapter = new BillManagerAdapter(ConfirmedListActivity.this, bills, Bill.DELIVERED_STATUS_TAG);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ConfirmedListActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    deliveryList.setAdapter(adapter);
                    deliveryList.setLayoutManager(layoutManager);
                    clearBill = true;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // not do anything here
            }
        });
    }
}