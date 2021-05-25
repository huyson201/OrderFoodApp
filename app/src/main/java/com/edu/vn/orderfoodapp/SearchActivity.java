package com.edu.vn.orderfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.vn.orderfoodapp.apdapters.CategoryAdapter;
import com.edu.vn.orderfoodapp.apdapters.FoodAdapter;
import com.edu.vn.orderfoodapp.models.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    //properties
    private ImageButton backBtn;
    private ArrayList<Food> foodList;
    private DatabaseReference database;
    private FoodAdapter foodAdapter;
    private Toolbar toolbar;
    private TextView textSearch;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        textSearch = findViewById(R.id.textViewSearch);
        toolbar = findViewById(R.id.tool_bar);
        setActionBar(toolbar);

        backBtn=findViewById(R.id.btn_back);
        // processing click back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String searchValue=getIntent().getStringExtra("text");
        textSearch.setText("Search for "+searchValue);

        recyclerView=findViewById(R.id.list_foods);


        foodList = new ArrayList<>();
        DatabaseReference databaseFood = FirebaseDatabase.getInstance().getReference("foods");
        databaseFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food food = dataSnapshot.getValue(Food.class);
                    if (food.getFoodName().toLowerCase().contains(searchValue.toLowerCase())) {
                        foodList.add(food);
                    }
                }
                foodAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        foodAdapter = new FoodAdapter(SearchActivity.this, foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(foodAdapter);

    }



}
