package com.edu.vn.orderfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.vn.orderfoodapp.apdapters.MenuItemAdapter;
import com.edu.vn.orderfoodapp.models.Food;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private TextView lblName,lblDecs,lblPrice;
    private ImageView imgFood;
    private ArrayList<Food> menus;
    private RecyclerView rvMenus;
    private DatabaseReference database;
    private FloatingActionButton btnAdd;
    private MenuItemAdapter menuAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        menus = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference("foods");
        rvMenus = findViewById(R.id.menus);
        //get value
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(database,Food.class).build();
        //set value to adapter
        menuAdapter = new MenuItemAdapter(options);
        LinearLayoutManager linearLayout =new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        linearLayout.setStackFromEnd(true); // reverse
        rvMenus.setLayoutManager(linearLayout);
        rvMenus.setAdapter(menuAdapter);
        //btn add
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AddFoodActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        menuAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        menuAdapter.startListening();
    }
}
