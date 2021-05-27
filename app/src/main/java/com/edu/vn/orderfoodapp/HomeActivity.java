package com.edu.vn.orderfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private ImageView imageView;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        categories = new ArrayList<Category>();
        database_Categories = FirebaseDatabase.getInstance().getReference("categories");

        //hien thi du lieu categories ra recylerview
        categoryRecyclerView = findViewById(R.id.categories);
        categoryAdapter = new CategoryAdapter(this, categories, this);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(categoryAdapter);
        //lay du lieu categories trong database
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

        //hien thi du lieu food ra rycylerview
        foodList = new ArrayList<>();
        foodRecyclerView = findViewById(R.id.list_foods);
        foodAdapter = new FoodAdapter(this, foodList);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        foodRecyclerView.setAdapter(foodAdapter);

        edtSearch=findViewById(R.id.editSearch);
        imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtSearch.getText().toString().equalsIgnoreCase(""))
                {
                    Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                    intent.putExtra("text",edtSearch.getText().toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }else {
                    Toast.makeText(HomeActivity.this,"Please Input Keyword !!",Toast.LENGTH_LONG).show();
                }
//                Log.d("edtSearch",edtSearch.getText().toString());
            }
        });
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