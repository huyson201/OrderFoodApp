package com.edu.vn.orderfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.edu.vn.orderfoodapp.apdapters.CategoryRecyclerViewAdapter;
import com.edu.vn.orderfoodapp.apdapters.FoodRecyclerViewAdapter;
import com.edu.vn.orderfoodapp.models.Category;
import com.edu.vn.orderfoodapp.models.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ArrayList<Category> categories = new ArrayList<Category>();
    private ArrayList<Food> foodList = new ArrayList<Food>();
    private CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;
    private FoodRecyclerViewAdapter foodRecyclerViewAdapter;
    private DatabaseReference database_Categories;
    private DatabaseReference database_Foods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        database_Categories = FirebaseDatabase.getInstance().getReference("categories");
        database_Foods = FirebaseDatabase.getInstance().getReference("foods");
        RecyclerView category_recyclerView = findViewById(R.id.categories);
        RecyclerView food_recyclerView = findViewById(R.id.list_foods);

        //
        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(this, R.layout.category_item, categories);
        LinearLayoutManager linearLayoutManager_horizontal=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        category_recyclerView.setLayoutManager(linearLayoutManager_horizontal);
        category_recyclerView.setAdapter(categoryRecyclerViewAdapter);

        database_Categories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);

                    categories.add(category);
                }
                categoryRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database_Foods.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Food food = dataSnapshot.getValue(Food.class);
                    foodList.add(food);
                }

                foodRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //
        foodRecyclerViewAdapter=new FoodRecyclerViewAdapter(this,R.layout.food_item_cardview,foodList);
        LinearLayoutManager linearLayoutManager_vertical=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        food_recyclerView.setLayoutManager(linearLayoutManager_vertical);
        food_recyclerView.setAdapter(foodRecyclerViewAdapter);
//



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

}