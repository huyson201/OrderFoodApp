package com.edu.vn.orderfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.edu.vn.orderfoodapp.apdapters.BillManagerAdapter;
import com.edu.vn.orderfoodapp.models.Bill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CookingActivity extends AppCompatActivity {
    //properties
    private RecyclerView cookingList;
    private ArrayList<Bill> bills;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private boolean clearBill = false;
    private BillManagerAdapter adapter;
    private TextView lblEmpty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cooking_layout);
        cookingList = findViewById(R.id.cooking_list);
        lblEmpty = findViewById(R.id.lbl_empty);

        lblEmpty.setVisibility(View.INVISIBLE);

        bills = new ArrayList<Bill>();

        // get all bills were confirmed and cooking
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
                        if(bill.getStatus().equals(Bill.CONFIRMED_STATUS_TAG) || bill.getStatus().equals(Bill.COOKING_STATUS_TAG)){
                            bills.add(bill);
                        }

                    }
                }
                if(bills.size() == 0){
                    lblEmpty.setVisibility(View.VISIBLE);
                    cookingList.setVisibility(View.INVISIBLE);
                }else{

                    lblEmpty.setVisibility(View.INVISIBLE);
                    cookingList.setVisibility(View.VISIBLE);

                    adapter = new BillManagerAdapter(CookingActivity.this, bills,Bill.CONFIRMED_STATUS_TAG);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(CookingActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    cookingList.setAdapter(adapter);
                    cookingList.setLayoutManager(layoutManager);
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