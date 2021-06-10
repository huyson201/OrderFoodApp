package com.edu.vn.orderfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.vn.orderfoodapp.apdapters.CategoryItemAdapter;
import com.edu.vn.orderfoodapp.models.Category;
import com.edu.vn.orderfoodapp.models.Food;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CategoryListActivity extends AppCompatActivity {

    private RecyclerView rvMenus;
    private DatabaseReference database;
    private FloatingActionButton btnAdd;
    private CategoryItemAdapter categoryItemAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        database = FirebaseDatabase.getInstance().getReference("categories");
        rvMenus = findViewById(R.id.menus);
        //get value
        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>().setQuery(database, Category.class).build();
        //set value to adapter
        categoryItemAdapter = new CategoryItemAdapter(options);
        LinearLayoutManager linearLayout =new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        linearLayout.setStackFromEnd(true); // reverse
        rvMenus.setLayoutManager(linearLayout);
        rvMenus.setAdapter(categoryItemAdapter);
        //btn add
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryListActivity.this, AddCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryItemAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        categoryItemAdapter.startListening();
    }
}
