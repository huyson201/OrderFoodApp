package com.edu.vn.orderfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.edu.vn.orderfoodapp.apdapters.BillManagerAdapter;
import com.edu.vn.orderfoodapp.apdapters.OrderedListBillAdapter;
import com.edu.vn.orderfoodapp.models.AdminMenu;
import com.edu.vn.orderfoodapp.models.Bill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfirmBillActivity extends AppCompatActivity {
    //properties
    private RecyclerView confirmList;
    private ArrayList<Bill> bills;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private boolean clearBill = false;
    private BillManagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_bill_layout);
        confirmList = findViewById(R.id.confirm_List);
        bills = new ArrayList<Bill>();

        db.child("bills").orderByChild("status").equalTo(Bill.CONFIRM_STATUS_TAG).addValueEventListener(new ValueEventListener() {
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

                adapter = new BillManagerAdapter(ConfirmBillActivity.this, bills, Bill.CONFIRM_STATUS_TAG);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ConfirmBillActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                confirmList.setAdapter(adapter);
                confirmList.setLayoutManager(layoutManager);
                clearBill = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // not do anything here
            }
        });

    }
}