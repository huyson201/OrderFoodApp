package com.edu.vn.orderfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import com.edu.vn.orderfoodapp.apdapters.CategoryAdapter;
import com.edu.vn.orderfoodapp.apdapters.FoodAdapter;
import com.edu.vn.orderfoodapp.models.Category;
import com.edu.vn.orderfoodapp.models.Food;
import com.edu.vn.orderfoodapp.models.UpdateRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements UpdateRecyclerView {
    private ArrayList<Category> categories;
    private ArrayList<Food> foodList;
    private DatabaseReference database_Categories;
    private RecyclerView categoryRecyclerView;
    private RecyclerView foodRecyclerView;
    private CategoryAdapter categoryAdapter;
    private FoodAdapter foodAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        categories = new ArrayList<Category>();
        database_Categories = FirebaseDatabase.getInstance().getReference("categories");

        categoryRecyclerView = findViewById(R.id.categories);
        categoryAdapter = new CategoryAdapter(this, categories, this);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(categoryAdapter);

        database_Categories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);
                    categories.add(category);
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        foodList = new ArrayList<>();
        foodRecyclerView = findViewById(R.id.list_foods);
        foodAdapter = new FoodAdapter(this, foodList);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        foodRecyclerView.setAdapter(foodAdapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public void callBack(int position, ArrayList<Food> foods) {
        foodAdapter = new FoodAdapter(this, foods);
        foodAdapter.notifyDataSetChanged();
        foodRecyclerView.setAdapter(foodAdapter);
    }

//
}